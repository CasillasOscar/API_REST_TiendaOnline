package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
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
    public ResponseEntity<?> getUsuarioByid(@PathVariable Integer id) throws CustomException {

            return ResponseEntity.ok(serviceCliente.getClienteById(id));
    }

    @GetMapping("/nickname/{nickname}")
    @Cacheable
    public ResponseEntity<?> getUsuarioByNickname(@PathVariable String nickname) throws CustomException {

            return ResponseEntity.ok(serviceCliente.getClienteByNickname(nickname));
    }

    @PostMapping("/login/{nickname}")
    public ResponseEntity<?> LoginByNicknameAndPassword(@PathVariable String nickname, @RequestBody Cliente user) throws CustomException {

        if(nickname.contentEquals(user.getNickname())){

                return ResponseEntity.ok(serviceCliente.LoginByNicknameAndPassword(user));

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }
    }

    @PostMapping
    public ResponseEntity<?> addCliente(@Valid @RequestBody Cliente cliente) throws CustomException {

            return ResponseEntity.ok(serviceCliente.addCliente(cliente));


    }

    @PutMapping("/{nickname}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, @PathVariable String nickname) throws CustomException{

        if(nickname.contentEquals(cliente.getNickname())){

                return ResponseEntity.ok(serviceCliente.updateCliente(cliente));

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }

    }

    @Transactional
    @DeleteMapping("/{nickname}")
    public ResponseEntity<?> deleteClienteByNicknameAndPassword(@RequestBody UsuarioLogDTO userDTO, @PathVariable String nickname) throws CustomException{

        if(nickname.contentEquals(userDTO.getNickname())){

            Boolean eliminado = serviceCliente.deleteClienteByNicknameAndPassword(userDTO);


            if (eliminado) {

                return ResponseEntity.ok("El usuario " + nickname + " ha sido eliminado");

            } else {
                return ResponseEntity.ok("El usuario " + nickname + " no ha sido eliminado");
            }

        } else {

            return ResponseEntity.badRequest().body("No coincide el identificador de la URI con el nickname del usuario");

        }
    }

}
