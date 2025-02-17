package com.proyecto.api_rest_tiendaonline.controllers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> getByNickname_Cliente(@PathVariable String nickname) throws CustomException{

            return ResponseEntity.ok(serviceHistorial.getByNickname_Cliente(nickname));
    }

    @GetMapping("/producto/{name}")
    public ResponseEntity<?> getByNombre_Producto(@PathVariable String name) throws CustomException{

            return ResponseEntity.ok(serviceHistorial.getByNombre_Producto(name));
    }

    @GetMapping("/fecha/{date}")
    public ResponseEntity<?> getByFecha(@PathVariable String date) throws CustomException{

            return ResponseEntity.ok(serviceHistorial.getByFecha(date));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getByTipo(@PathVariable String tipo) throws CustomException{

            return ResponseEntity.ok(serviceHistorial.getByTipo(tipo));

    }


    @PostMapping("/comprar/{nickname}")
    public ResponseEntity<?> comprar_producto(@PathVariable String nickname, @RequestBody CompraProductoDTO compraProductoDTO) throws CustomException {

            return ResponseEntity.of(serviceHistorial.comprar_producto(nickname, compraProductoDTO));

    }

    @PostMapping("/devolucion/{nickname}")
    public ResponseEntity<?> devolver_producto(@PathVariable String nickname, @RequestBody DevolucionProductoDTO devolucionProductoDTO) throws CustomException{

            return ResponseEntity.of(serviceHistorial.devolver_producto(nickname, devolucionProductoDTO));

    }

    //No se puede eliminar o editar un historial (logica de negocio)

}
