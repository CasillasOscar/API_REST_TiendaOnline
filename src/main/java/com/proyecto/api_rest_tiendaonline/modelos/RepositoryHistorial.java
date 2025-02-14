package com.proyecto.api_rest_tiendaonline.modelos;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryHistorial extends JpaRepository<Historial, Integer> {

    List<Historial> getHistorialsByCliente_Id(Integer clienteId);

    List<Historial> getHistorialsByProducto_Id(Integer productoId);

    List<Historial> getHistorialsByTipo(@Size(max = 100) String tipo);

    Optional<List<Historial>> getHistorialsByFechaCompra(LocalDate fechaCompra);

    Optional<Historial> getHistorialByCliente_IdAndProducto_IdAndTipoIsLikeAndDescripcionNull(Integer id, Integer id1, String compra);
}
