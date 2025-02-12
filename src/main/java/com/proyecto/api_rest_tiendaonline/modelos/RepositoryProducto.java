package com.proyecto.api_rest_tiendaonline.modelos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryProducto extends JpaRepository<Producto, Integer> {
    Optional<Producto> getProductoById(Integer id);

    Optional<Producto> getProductoByNombre(@Size(max = 100) @NotNull String nombre);

}
