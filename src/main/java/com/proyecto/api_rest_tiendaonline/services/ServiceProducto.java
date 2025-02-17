package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.Producto;

import com.proyecto.api_rest_tiendaonline.modelos.RepositoryProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Optional<Producto> getProductById(Integer id)  throws CustomException {

        Optional<Producto> productExist = repositoryProducto.getProductoById(id);

        if(productExist.isPresent()){

            return productExist;

        } else {

            throw new CustomException("El producto con id " + id + " no existe");

        }

    }

    @Override
    public Optional<Producto> getProductByName(String name)  throws CustomException{

        Optional<Producto> productExist = repositoryProducto.getProductoByNombre(name);

        if(productExist.isPresent()){

            return productExist;

        } else {

            throw new CustomException("El producto " + name + " no existe");

        }

    }


    @Override
    public Optional<Producto> addProducto(Producto producto) throws CustomException {

        //Validacion de producto con nombre unico
        Optional<Producto> productExist = repositoryProducto.getProductoByNombre(producto.getNombre());

        if(productExist.isEmpty()){

            if(producto.getPrecio().compareTo(BigDecimal.valueOf(10)) == -1){
                producto.setDescripcion(producto.getDescripcion().concat(" producto en oferta"));
            }

            if(producto.getPrecio().compareTo(BigDecimal.valueOf(200)) == 1){
                producto.setDescripcion(producto.getDescripcion().concat(" producto de calidad"));
            }

            repositoryProducto.save(producto);
            return Optional.of(producto);

        } else {

            throw new CustomException("El producto " + producto.getNombre() + " ya existe");

        }

    }

    @Override
    public Optional<Producto> updateProducto(Producto producto)  throws CustomException{

            Optional<Producto> productExist = repositoryProducto.getProductoByNombre(producto.getNombre());

            if(productExist.isPresent()){

                productExist.get().setPrecio(producto.getPrecio());
                productExist.get().setDescripcion(producto.getDescripcion());

                repositoryProducto.save(productExist.get());
                return productExist;

            } else {

                throw new CustomException("El producto " + producto.getNombre() + " no existe");

            }
    }


    @Override
    public Boolean deleteProductoById(Integer id)  throws CustomException{

            Optional<Producto> productExist = repositoryProducto.getProductoById(id);

            if(productExist.isPresent()){

                    repositoryProducto.delete(productExist.get());
                    return true;

            } else {

                throw new CustomException("El producto con id " + id + " no existe");

            }

    }
}
