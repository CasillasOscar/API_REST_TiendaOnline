package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.modelos.Cliente;
import com.proyecto.api_rest_tiendaonline.modelos.UsuarioLogDTO;
import com.proyecto.api_rest_tiendaonline.services.ServiceCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CacheConfig(cacheNames = {"cliente"})
public class ControllerCliente {

    private final ServiceCliente serviceCliente;

    @Autowired
    public ControllerCliente(ServiceCliente serviceCliente) {
        this.serviceCliente = serviceCliente;
    }


    @GetMapping
    public ResponseEntity<?> getAllUsuarios(){
        return ResponseEntity.ok(serviceCliente.getAllCLientes());
    }


    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<?> getUsuarioByid(@PathVariable Integer id) {

        Optional<Cliente> clienteExist = serviceCliente.getClienteById(id);

        if (clienteExist.isPresent()) {

            return ResponseEntity.ok(clienteExist);

        } else {

            return ResponseEntity.badRequest().body("No se ha encontrado un cliente con dicho id");
        }

    }

    @GetMapping("/nickname/{nickname}")
    @Cacheable
    public ResponseEntity<?> getUsuarioByNickname(@PathVariable String nickname) {

        Optional<Cliente> clienteExist = serviceCliente.getClienteByNickname(nickname);

        if (clienteExist.isPresent()) {

            return ResponseEntity.ok(clienteExist);

        } else {

            return ResponseEntity.badRequest().body("No se ha encontrado un cliente con dicho nickname");
        }

    }

    @PostMapping("/login/{nickname}")
    public ResponseEntity<?> LoginByNicknameAndPassword(@PathVariable String nickname,@Valid @RequestBody Cliente user) {

        if(nickname.contentEquals(user.getNickname())){

            Optional<Cliente> clienteExist = serviceCliente.LoginByNicknameAndPassword(user);

            if (clienteExist.isPresent()) {

                return ResponseEntity.ok(clienteExist);

            } else {

                return ResponseEntity.badRequest().body("No existe dicho nickname y password");
            }

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }
    }

    @PostMapping
    public ResponseEntity<?> addCliente(@Valid @RequestBody Cliente cliente) {

        Optional<Cliente> clienteExist = serviceCliente.addCliente(cliente);


        if (clienteExist.isPresent()) {

            return ResponseEntity.ok(clienteExist);

        } else {

            return ResponseEntity.badRequest().body("No se ha creado el cliente");
        }

    }

    @PutMapping("/{nickname}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, @PathVariable String nickname) {

        if(nickname.contentEquals(cliente.getNickname())){

            Optional<Cliente> clienteExist = serviceCliente.updateCliente(cliente);


            if (clienteExist.isPresent()) {

                return ResponseEntity.ok(clienteExist);

            } else {

                return ResponseEntity.badRequest().body("No se ha actualizado el cliente");
            }

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }

    }

    @Transactional
    @DeleteMapping("/{nickname}")
    public ResponseEntity<?> deleteClienteByNicknameAndPassword(@RequestBody UsuarioLogDTO userDTO, @PathVariable String nickname){

        if(nickname.contentEquals(userDTO.getNickname())){

            Boolean eliminado = serviceCliente.deleteClienteByNicknameAndPassword(userDTO);


            if (eliminado) {

                return ResponseEntity.ok("El usuario " + nickname + " ha sido eliminado");

            } else {

                return ResponseEntity.badRequest().body("No se ha eliminado el usuario");
            }

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }
    }

}
