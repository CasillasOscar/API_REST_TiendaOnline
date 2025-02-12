package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.modelos.Cliente;
import com.proyecto.api_rest_tiendaonline.modelos.UsuarioLogDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceInterface {

    //GET ALL CLIENTES
    List<Cliente> getAllCLientes();

    //GET CLIENTE BY ID
    Optional<Cliente> getClienteById(Integer id);

    //GET CLIENTE BY NICKNAME
    Optional<Cliente> getClienteByNickname(String nickname);

    //LOG IN => CLIENTE MANDA NICKNAME Y PWD
    Optional<Cliente> LoginByNicknameAndPassword(Cliente user);

    //SIGN IN => CLIENTE ENVIA SUS DATOS PARA CREAR UN USUARIO EN LA BD
    Optional<Cliente> addCliente(Cliente cliente);

    //UPDATE => SE ENVIA DATOS DEL CLIENTE DONDE UNICAMENTE SE PUEDEN ACTAULIZAR LA PWD, TLF, DOMICILIO
    Optional<Cliente> updateCliente(Cliente cliente);

    //BORRAR CLIENTE DE LA BD => EL CLIENTE ENVIA SU NICKNAME Y PWD PARA CONFIRMAR LA ELIMINACIÓN
    Boolean deleteClienteByNicknameAndPassword(UsuarioLogDTO user);

}
