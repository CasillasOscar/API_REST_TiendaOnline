package com.proyecto.api_rest_tiendaonline.services;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import com.proyecto.api_rest_tiendaonline.modelos.Cliente;
import com.proyecto.api_rest_tiendaonline.modelos.RepositoryCliente;
import com.proyecto.api_rest_tiendaonline.modelos.UsuarioLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceCliente implements ClienteServiceInterface{

    @Autowired
    private RepositoryCliente respositoryCliente;
    @Autowired
    private RepositoryCliente repositoryCliente;

    //GET ALL

    @Override
    public List<Cliente> getAllCLientes() {
        return respositoryCliente.findAll();
    }

    @Override
    public Optional<Cliente> getClienteById(Integer id) throws CustomException {

            Optional<Cliente> clienteExist = repositoryCliente.getClienteById(id);

            if(clienteExist.isPresent()){

                return clienteExist;

            } else {

                throw new CustomException("No existe el cliente con id " + id);
            }



    }

    //GET by nickname
    @Override
    public Optional<Cliente> getClienteByNickname(String nickname) throws CustomException{

        Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(nickname);

        if(clienteExist.isPresent()){

            return clienteExist;

        } else {

            throw new CustomException("No existe el cliente con nickname " + nickname);
        }
    }

    //Login for nickname and password
    @Override
    public Optional<Cliente> LoginByNicknameAndPassword(Cliente user) throws CustomException{

            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(user.getNickname());

            if(clienteExist.isPresent()){

                clienteExist = repositoryCliente.getClienteByNicknameAndPassword(user.getNickname(), user.getPassword());

                if(clienteExist.isPresent()){

                    return clienteExist;

                } else {

                    throw new CustomException("La contraseña de " + user.getNickname() + " no es correcta");

                }

            } else {

               throw new CustomException("El nickname " + user.getNickname() + " no existe");

            }


    }

    // Add cliente => Comprueba si el nickname ya existe puesto que campo único
    @Override
    public Optional<Cliente> addCliente(Cliente cliente) throws CustomException {

       Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(cliente.getNickname());

       if(clienteExist.isEmpty()){

           repositoryCliente.save(cliente);
           return Optional.of(cliente);

       } else {

           throw new CustomException("El nickname " + cliente.getNickname() + " ya existe en la base de datos.");

       }
    }

    //Update de cliente => Únicamente puede cambiar la contraseña, teléfono o domicilio
    @Override
    public Optional<Cliente> updateCliente(Cliente cliente) throws CustomException{



            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(cliente.getNickname());

            if(clienteExist.isPresent()){
                clienteExist.get().setPassword(cliente.getPassword());
                clienteExist.get().setDomicilio(cliente.getDomicilio());
                clienteExist.get().setTelefono(cliente.getTelefono());

                repositoryCliente.save(clienteExist.get());
                return clienteExist;

            } else {

                throw new CustomException("El cliente con nickname " + cliente.getNickname() + " no existe.");

            }



    }


    @Override
    public Boolean deleteClienteByNicknameAndPassword(UsuarioLogDTO user) throws CustomException {

            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(user.getNickname());

            if(clienteExist.isPresent()){

                clienteExist = repositoryCliente.getClienteByNicknameAndPassword(user.getNickname(), user.getPassword());

                if(clienteExist.isPresent()){

                    repositoryCliente.delete(clienteExist.get());
                    return true;

                } else {

                    throw new CustomException("La contraseña de " + user.getNickname() + " no es correcta");

                }

            } else {

                throw new CustomException("El cliente con nickname " + user.getNickname() + " no existe.");

            }

    }
}
