package com.proyecto.api_rest_tiendaonline.modelos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryCliente extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> getClienteById(Integer id);

    Optional<Cliente> getClienteByNicknameAndPassword(@Size(max = 50) @NotNull String nickname, @Size(max = 255) @NotNull String password);

    Optional<Cliente> getClienteByNickname(String nickname);


}
