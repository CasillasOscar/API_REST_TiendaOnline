package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.modelos.CompraProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.DevolucionProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.Historial;
import com.proyecto.api_rest_tiendaonline.modelos.Producto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistorialServiceInterface {

    List<Historial> getAll();

    Optional<List<Historial>> getByNickname_Cliente(String nickname);
    //Se puede hacer por cliente, con fecha
    //Se puede hacer por cliente, con tipo
    //Se puede hacer por cliente, con producto

    Optional<List<Historial>> getByNombre_Producto(String nombreProducto);
    //Se puede hacer por producto, con fecha
    //Se puede hacer por producto, con tipo

    Optional<List<Historial>> getByFecha(LocalDate date);

    Optional<List<Historial>> getByTipo(String tipo);


    Optional<Historial> comprar_producto(String nickname, CompraProductoDTO compraProductoDTO);
    Optional<Historial> devolver_producto(String nickname, DevolucionProductoDTO devolucionProductoDTO);

}
