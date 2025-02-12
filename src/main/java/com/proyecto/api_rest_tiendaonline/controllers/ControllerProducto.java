package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.modelos.Producto;
import com.proyecto.api_rest_tiendaonline.services.ServiceProducto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {

        Optional<Producto> productExist = serviceProducto.getProductById(id);

        if (productExist.isPresent()) {

            return ResponseEntity.ok(productExist.get());

        } else {

            return ResponseEntity.badRequest().body("No se ha encontrado un producto con dicho id");
        }
    }

    @GetMapping("/name/{name}")
    @Cacheable
    public ResponseEntity<?> getProductByName(@PathVariable String name) {

        Optional<Producto> productExist = serviceProducto.getProductByName(name);

        if (productExist.isPresent()) {

            return ResponseEntity.ok(productExist);

        } else {

            return ResponseEntity.badRequest().body("No se ha encontrado un producto con ese nombre");
        }

    }

    @PostMapping
    public ResponseEntity<?> addProducto(@Valid @RequestBody Producto producto){

        Optional<Producto> productoCreate = serviceProducto.addProducto(producto);

        if(productoCreate.isPresent()){

            return ResponseEntity.ok(productoCreate);

        } else {

            return ResponseEntity.badRequest().body("No se ha podido crear el producto en la base de datos");
        }
    }



}
