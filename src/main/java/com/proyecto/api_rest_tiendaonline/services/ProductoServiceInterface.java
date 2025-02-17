package com.proyecto.api_rest_tiendaonline.services;


import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServiceInterface {

    List<Producto> getAllProducts();
    Optional<Producto> getProductById(Integer id) throws CustomException;
    Optional<Producto> getProductByName(String name) throws CustomException;

    Optional<Producto> addProducto(Producto producto) throws CustomException;

    Optional<Producto> updateProducto(Producto producto) throws CustomException;

    Boolean deleteProductoById(Integer id) throws CustomException;
}
