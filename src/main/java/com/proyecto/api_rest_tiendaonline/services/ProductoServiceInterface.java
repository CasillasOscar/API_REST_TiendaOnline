package com.proyecto.api_rest_tiendaonline.services;


import com.proyecto.api_rest_tiendaonline.modelos.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServiceInterface {

    List<Producto> getAllProducts();
    Optional<Producto> getProductById(Integer id);
    Optional<Producto> getProductByName(String name);

    Optional<Producto> addProducto(Producto producto);

    Optional<Producto> updateProducto(Producto producto);

    Boolean deleteProductoById(Integer id);
}
