package com.proyecto.api_rest_tiendaonline.services;

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
    public Optional<Cliente> getClienteById(Integer id) {

        try{

            return repositoryCliente.getClienteById(id);

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }

    //GET by nickname
    @Override
    public Optional<Cliente> getClienteByNickname(String nickname) {

        try{

            return repositoryCliente.getClienteByNickname(nickname);

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }

    //Login for nickname and password
    @Override
    public Optional<Cliente> LoginByNicknameAndPassword(Cliente user) {
        try{

            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(user.getNickname());

            if(clienteExist.isPresent()){

                clienteExist = repositoryCliente.getClienteByNicknameAndPassword(user.getNickname(), user.getPassword());

                if(clienteExist.isPresent()){

                    return clienteExist;

                } else {

                    return Optional.empty();

                }

            } else {

               return Optional.empty();

            }
        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }

    // Add cliente => Comprueba si el nickname ya existe puesto que campo único
    @Override
    public Optional<Cliente> addCliente(Cliente cliente) {

       Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(cliente.getNickname());

       if(clienteExist.isEmpty()){

           repositoryCliente.save(cliente);
           return Optional.of(cliente);

       } else {

           return Optional.empty();

       }
    }

    //Update de cliente => Únicamente puede cambiar la contraseña, teléfono o domicilio
    @Override
    public Optional<Cliente> updateCliente(Cliente cliente) {

        try{

            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(cliente.getNickname());

            if(clienteExist.isPresent()){
                clienteExist.get().setPassword(cliente.getPassword());
                clienteExist.get().setDomicilio(cliente.getDomicilio());
                clienteExist.get().setTelefono(cliente.getTelefono());

                repositoryCliente.save(clienteExist.get());
                return clienteExist;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }


    @Override
    public Boolean deleteClienteByNicknameAndPassword(UsuarioLogDTO user) {

        try{

            Optional<Cliente> clienteExist = repositoryCliente.getClienteByNickname(user.getNickname());

            if(clienteExist.isPresent()){

                clienteExist = repositoryCliente.getClienteByNicknameAndPassword(user.getNickname(), user.getPassword());

                if(clienteExist.isPresent()){

                    repositoryCliente.delete(clienteExist.get());
                    return true;

                } else {

                    return false;

                }

            } else {

                return false;

            }
        } catch (NoSuchElementException e) {

            return false;

        }
    }
}
