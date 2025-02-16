package com.proyecto.api_rest_tiendaonline.modelos;

import jakarta.validation.constraints.Pattern;

public class UsuarioLogDTO {

    private String nickname;
    private String password;

    public UsuarioLogDTO(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
