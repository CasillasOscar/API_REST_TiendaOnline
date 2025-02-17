package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.CompraProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.DevolucionProductoDTO;
import com.proyecto.api_rest_tiendaonline.modelos.Historial;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistorialServiceInterface {

    List<Historial> getAll();

    Optional<List<Historial>> getByNickname_Cliente(String nickname) throws CustomException;
    //Se puede hacer por cliente, con fecha
    //Se puede hacer por cliente, con tipo
    //Se puede hacer por cliente, con producto

    Optional<List<Historial>> getByNombre_Producto(String nombreProducto) throws CustomException;
    //Se puede hacer por producto, con fecha
    //Se puede hacer por producto, con tipo

    Optional<List<Historial>> getByFecha(LocalDate date) throws CustomException;

    Optional<List<Historial>> getByTipo(String tipo) throws CustomException;


    Optional<Historial> comprar_producto(String nickname, CompraProductoDTO compraProductoDTO) throws CustomException;
    Optional<Historial> devolver_producto(String nickname, DevolucionProductoDTO devolucionProductoDTO) throws CustomException;

}
