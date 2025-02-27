package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceHistorial implements HistorialServiceInterface{

    RepositoryHistorial repositoryHistorial;
    RepositoryCliente repositoryCliente;
    RepositoryProducto repositoryProducto;

    @Autowired
    public ServiceHistorial(RepositoryHistorial repositoryHistorial, RepositoryCliente repositoryCliente, RepositoryProducto repositoryProducto) {
        this.repositoryHistorial = repositoryHistorial;
        this.repositoryCliente = repositoryCliente;
        this.repositoryProducto = repositoryProducto;
    }

    @Override
    public List<Historial> getAll() {
        return repositoryHistorial.findAll();
    }

    @Override
    public Optional<List<Historial>> getByNickname_Cliente(String nickname) throws CustomException {

        Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(nickname);

        if(clienteExist.isPresent()){

            List<Historial> listExist = repositoryHistorial.getHistorialsByCliente_Id(clienteExist.get().getId());

            if(!listExist.isEmpty()){

                return Optional.of(listExist);

            } else {

                throw new CustomException("No hay historial del cliente " + nickname);

            }

        } else {

            throw new CustomException("No existe el cliente con nickname " + nickname);
        }

    }

    @Override
    public Optional<List<Historial>> getByNombre_Producto(String nombre_producto) throws CustomException {

        Optional<Producto> productExist = repositoryProducto.getProductoByNombre(nombre_producto);

        if(productExist.isPresent()){

            List<Historial> listExist = repositoryHistorial.getHistorialsByProducto_Id(productExist.get().getId());

            if(!listExist.isEmpty()){

                return Optional.of(listExist);

            } else {

                throw new CustomException("No hay historial del producto " + nombre_producto);

            }

        } else {

            throw new CustomException("No existe el producto " + nombre_producto);
        }
    }

    @Override
    public Optional<List<Historial>> getByFecha(String date) throws CustomException {

        String arrayDate[] = date.split("-");

        if(arrayDate.length == 3){

            try {

                Optional<LocalDate> dateOK = Optional.of(LocalDate.of(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0])));

                if (dateOK.isPresent() && dateOK.get() instanceof LocalDate) {

                    Optional<List<Historial>> list = repositoryHistorial.getHistorialsByFechaCompra(dateOK.get());

                    if (!list.get().isEmpty()) {

                        return Optional.of(list.get());

                    } else {

                        throw new CustomException("No existe historial para la fecha " + date);

                    }

                } else {

                    throw new CustomException("Formato de fecha incorrecto: dd-MM-YYY");

                }

            }   catch(NumberFormatException e){

                throw new CustomException("Formato de fecha incorrecto: dd-MM-YYY");

            }   catch(DateTimeException e){

                throw new CustomException("Formato de fecha incorrecto: dias maximo 31 y meses 12");
            }
        } else {

            throw new CustomException("Formato de fecha incorrecto: dd-MM-YYY");
        }
    }

    @Override
    public Optional<List<Historial>> getByTipo(String tipo) throws CustomException {

        if(
                tipo.equalsIgnoreCase("compra") ||
                tipo.equalsIgnoreCase("devolucion")
        ){

            List<Historial> list = repositoryHistorial.getHistorialsByTipo(tipo);

            if(!list.isEmpty()){

                return Optional.of(list);

            } else {

                throw new CustomException("No existe historial para el tipo " + tipo);

            }


        } else {

            throw new CustomException("El tipo " + tipo + " no existe");
        }

    }

    //Comprobaciones de stock y clienteExist
    @Override
    public Optional<Historial> comprar_producto(String nickname, CompraProductoDTO compraProductoDTO) throws CustomException {

        Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(nickname);

        if(clienteExist.isPresent()){

            Optional<Producto> productoExist = repositoryProducto.getProductoByNombre(compraProductoDTO.getNombre());

            if(productoExist.isPresent()){

                if (productoExist.get().getStock() >= compraProductoDTO.getCantidad()){

                    Historial newHistorial = new Historial();
                    newHistorial.setProducto(productoExist.get());
                    newHistorial.setCliente(clienteExist.get());
                    newHistorial.setCantidad(compraProductoDTO.getCantidad());
                    newHistorial.setTipo("Compra");
                    newHistorial.setFechaCompra(LocalDate.now());

                    //Se actualiza el stock
                    productoExist.get().setStock(productoExist.get().getStock() - compraProductoDTO.getCantidad());
                    repositoryProducto.save(productoExist.get());

                    repositoryHistorial.save(newHistorial);

                    return Optional.of(newHistorial);

                } else {

                    throw new CustomException("No hay stock suficiente");

                }

            } else {

                throw new CustomException("El producto no existe");

            }

        } else {

            throw new CustomException("El cliente no existe");

        }

    }

    //Comprobaciones de historial con compra, menos de 30 dias desde la compra y clienteExist
    @Override
    public Optional<Historial> devolver_producto(String nickname, DevolucionProductoDTO devolucionProductoDTO) throws CustomException {

        Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(nickname);

        //Cliente existe
        if(clienteExist.isPresent()){

            Optional<Producto> productoExist = repositoryProducto.getProductoByNombre(devolucionProductoDTO.getNombre());

            //Producto existe
            if(productoExist.isPresent()){

                Optional<Historial> historialExist = repositoryHistorial.getHistorialByCliente_IdAndProducto_IdAndTipoIsLikeAndDescripcionNull(clienteExist.get().getId(), productoExist.get().getId(), "Compra");

                //Historial de compra existe para ese cliente, producto y es de tipo compra y la descripcion no es Devuelto (Problema si hay dos vacios, se soluciona pasando fecha en la devolucion, como si fuese un ticket o con el id de la compra)
                if(historialExist.isPresent()){

                    //La fecha de devolucion es antes de 30 dias desde la compra
                    if(
                            LocalDate.now().isBefore(historialExist.get().getFechaCompra().plusDays(30)) ||
                            LocalDate.now().isEqual(historialExist.get().getFechaCompra().plusDays(30))
                    ){
                        //La cantidad que se quiere devolver es menor o igual a la del historial de compra
                        if(devolucionProductoDTO.getCantidad() <= historialExist.get().getCantidad()){

                            Historial devolucion = new Historial();
                            devolucion.setCliente(clienteExist.get());
                            devolucion.setProducto(productoExist.get());
                            devolucion.setFechaCompra(LocalDate.now());
                            devolucion.setCantidad(devolucionProductoDTO.getCantidad());
                            devolucion.setTipo("Devolucion");
                            devolucion.setDescripcion(devolucionProductoDTO.getDescripcion());

                            repositoryHistorial.save(devolucion);

                            //Se añade a stock
                            productoExist.get().setStock(productoExist.get().getStock() + devolucionProductoDTO.getCantidad());

                            repositoryProducto.save(productoExist.get());

                            //
                            historialExist.get().setDescripcion("Devuelto");
                            repositoryHistorial.save(historialExist.get());

                            return Optional.of(devolucion);

                        } else {

                            throw new CustomException("La cantidad del historial es " + historialExist.get().getCantidad() + " y la que quiere devolver es " + devolucionProductoDTO.getCantidad() + " debe coincidir o ser menor a la cantidad que se compró");

                        }

                    } else {

                        throw new CustomException("La fecha de compra tiene como límite 30 días, no se puede devolver, fecha de compra: " + historialExist.get().getFechaCompra());

                    }


                } else {

                    throw new CustomException("No existe un historial de compra del cliente " + nickname + " con el producto " + devolucionProductoDTO.getNombre() + " que sea de tipo compra");

                }

            } else {

                throw new CustomException("El producto " + devolucionProductoDTO.getNombre() + " no existe");

            }

        } else {

            throw new CustomException("El cliente " + nickname + " no existe");

        }
    }
}
