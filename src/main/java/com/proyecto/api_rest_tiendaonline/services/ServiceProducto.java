package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.modelos.Producto;

import com.proyecto.api_rest_tiendaonline.modelos.RepositoryProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceProducto implements ProductoServiceInterface {

    @Autowired
    private RepositoryProducto repositoryProducto;

    @Override
    public List<Producto> getAllProducts() {
        return repositoryProducto.findAll();
    }

    @Override
    public Optional<Producto> getProductById(Integer id) {

        try{

            return repositoryProducto.getProductoById(id);

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Producto> getProductByName(String name) {

        try{

            return repositoryProducto.getProductoByNombre(name);

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }


    @Override
    public Optional<Producto> addProducto(Producto producto) {

        Optional<Producto> productExist = repositoryProducto.getProductoByNombre(producto.getNombre());

        if(productExist.isEmpty()){

            repositoryProducto.save(producto);
            return Optional.of(producto);

        } else {

            return Optional.empty();

        }

    }

    @Override
    public Optional<Producto> updateProducto(Producto producto) {

        try{

            Optional<Producto> productExist = repositoryProducto.getProductoByNombre(producto.getNombre());

            if(productExist.isPresent()){

                productExist.get().setPrecio(producto.getPrecio());
                productExist.get().setDescripcion(producto.getDescripcion());

                repositoryProducto.save(productExist.get());
                return productExist;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }


    @Override
    public Boolean deleteProductoById(Integer id) {

        try{

            Optional<Producto> productExist = repositoryProducto.getProductoById(id);

            if(productExist.isPresent()){


                    repositoryProducto.delete(productExist.get());
                    return true;


            } else {

                return false;

            }
        } catch (NoSuchElementException e) {

            return false;

        }
    }
}
