package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.modelos.CompraProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.DevolucionProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.Historial;
import com.proyecto.api_rest_tiendaonline.services.ServiceHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historial")
@CacheConfig
public class ControllerHistorial {

    @Autowired
    ServiceHistorial serviceHistorial;

    @GetMapping
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok(serviceHistorial.getAll());

    }

    @GetMapping("/cliente/{nickname}")
    public ResponseEntity<?> getByNickname_Cliente(@PathVariable String nickname){

        Optional<List<Historial>> listOfClient = serviceHistorial.getByNickname_Cliente(nickname);

        if(listOfClient.isPresent()){

            return ResponseEntity.ok(listOfClient);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido sacar el historial del cliente con nickname " + nickname);

        }

    }

    @GetMapping("/producto/{name}")
    public ResponseEntity<?> getByNombre_Producto(@PathVariable String name){

        Optional<List<Historial>> listOfClient = serviceHistorial.getByNombre_Producto(name);

        if(listOfClient.isPresent()){

            return ResponseEntity.ok(listOfClient);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido sacar el historial del producto " + name);

        }

    }

    @GetMapping("/fecha/{date}")
    public ResponseEntity<?> getByFecha(@PathVariable String date){

        String arrayDate[] = date.split("-");

        if(arrayDate.length == 3){

            try{

                Optional<LocalDate> dateOK = Optional.of(LocalDate.of(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0])));

                if(dateOK.isPresent() && dateOK.get() instanceof LocalDate){

                    Optional<List<Historial>> list = serviceHistorial.getByFecha(dateOK.get());

                    if(list.isPresent()){

                        return ResponseEntity.ok(list);

                    } else {

                        return ResponseEntity.badRequest().body("No se ha podido sacar el historial de la fecha " + date);

                    }
                } else {

                    return ResponseEntity.badRequest().body("Formato incorrecto de fecha: dd-MM-YYYY");
                }

            } catch (NumberFormatException e) {

                return ResponseEntity.badRequest().body("Formato de fecha incorrecto: dd--MM--YYYY");

            } catch (DateTimeException e) {

                return ResponseEntity.badRequest().body("Formato de fecha incorrecto: dias maximo 31 y meses 12");

            }

        } else {

            return ResponseEntity.badRequest().body("Formato incorrecto de fecha: dd-MM-YYYY");

        }


    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getByTipo(@PathVariable String tipo){

        Optional<List<Historial>> list = serviceHistorial.getByTipo(tipo);

        if(list.isPresent() || list.get().size() > 0){

            return ResponseEntity.ok(list);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido sacar el historial del tipo: " + tipo);

        }

    }


    @PostMapping("/comprar/{nickname}")
    public ResponseEntity<?> comprar_producto(@PathVariable String nickname, @RequestBody CompraProductoDTO compraProductoDTO){

        Optional<Historial> newHistorial = serviceHistorial.comprar_producto(nickname, compraProductoDTO);

        if(newHistorial.isPresent()){

            return ResponseEntity.of(newHistorial);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido comprar el producto");
        }
    }

    @PostMapping("/devolucion/{nickname}")
    public ResponseEntity<?> devolver_producto(@PathVariable String nickname, @RequestBody DevolucionProductoDTO devolucionProductoDTO){

        Optional<Historial> newHistorial = serviceHistorial.devolver_producto(nickname, devolucionProductoDTO);

        if(newHistorial.isPresent()){

            return ResponseEntity.of(newHistorial);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido devolver el producto");
        }
    }

    //No se puede eliminar o editar un historial (logica de negocio)

}
