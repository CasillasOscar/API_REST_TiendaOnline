package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.Producto;
import com.proyecto.api_rest_tiendaonline.services.ServiceProducto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/producto")
@CacheConfig(cacheNames = {"producto"})
public class ControllerProducto {

    private final ServiceProducto serviceProducto;

    @Autowired
    public ControllerProducto(ServiceProducto serviceProducto) {
        this.serviceProducto = serviceProducto;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(serviceProducto.getAllProducts());
    }


    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<?> getProductById(@PathVariable Integer id) throws CustomException {

            return ResponseEntity.ok(serviceProducto.getProductById(id));

    }

    @GetMapping("/name/{name}")
    @Cacheable
    public ResponseEntity<?> getProductByName(@PathVariable String name) throws CustomException {

            return ResponseEntity.ok(serviceProducto.getProductByName(name));

    }

    @PostMapping
    public ResponseEntity<?> addProducto(@Valid @RequestBody Producto producto) throws CustomException{

            return ResponseEntity.ok(serviceProducto.addProducto(producto));

    }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateProducto(@Valid @RequestBody Producto producto) throws CustomException{

            return ResponseEntity.of(serviceProducto.updateProducto(producto));

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id) throws CustomException{

        Boolean eliminado = serviceProducto.deleteProductoById(id);

        if(eliminado){

            return ResponseEntity.ok("El producto con id " + id + " ha sido eliminado correctamente");

        } else {

            return ResponseEntity.badRequest().body("El producto con id " + id + " no ha podido ser eliminado");

        }

    }
    
}
