package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    public Optional<List<Historial>> getByNickname_Cliente(String nickname) {

        Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(nickname);

        if(clienteExist.isPresent()){

            return Optional.of(repositoryHistorial.getHistorialsByCliente_Id(clienteExist.get().getId()));

        } else {

            return Optional.empty();
        }

    }

    @Override
    public Optional<List<Historial>> getByNombre_Producto(String nombre_producto) {

        Optional<Producto> productoExist = repositoryProducto.getProductoByNombre(nombre_producto);

        if(productoExist.isPresent()){

            return Optional.of(repositoryHistorial.getHistorialsByProducto_Id(productoExist.get().getId()));

        } else {

            return Optional.empty();
        }

    }

    @Override
    public Optional<List<Historial>> getByFecha(LocalDate date) {

        Optional<List<Historial>> list = repositoryHistorial.getHistorialsByFechaCompra(date);

        if(list.get().size() > 0){

            return Optional.of(list.get());

        } else {

            return Optional.empty();

        }

    }

    @Override
    public Optional<List<Historial>> getByTipo(String tipo) {

        if(
                tipo.equalsIgnoreCase("compra") ||
                tipo.equalsIgnoreCase("devolucion")
        ){

            return Optional.of(repositoryHistorial.getHistorialsByTipo(tipo));


        } else {

            return Optional.empty();
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
    public Optional<Historial> devolver_producto(String nickname, DevolucionProductoDTO devolucionProductoDTO) {

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

                            return Optional.empty();

                        }

                    } else {

                        return Optional.empty();

                    }


                } else {

                    return Optional.empty();

                }

            } else {

                return Optional.empty();

            }

        } else {

            return Optional.empty();

        }
    }
}
