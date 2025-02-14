package com.proyecto.api_rest_tiendaonline.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "Solo son válidas letras con la primera mayuscula y el resto minusculas")
    @Size(min = 1, max = 50, message = "Minimo 1 letra y maximo 50")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "Solo son válidas letras con la primera mayuscula y el resto minusculas")
    @Size(min = 1, max = 50, message = "Minimo 1 letra y maximo 50")
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @NotNull
    @Size(min = 1, max = 50, message = "Minimo 1 letra y maximo 50")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo son admitidas letras y numeros")
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @NotNull
    @Size(min = 1, max = 12, message = "Minimo 1 letra y maximo 12")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo son admitidas letras y numeros")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(min = 9, max = 9)
    @Pattern(regexp = "^(6|9)\\d{8}$", message = "Tienen que ser 9 digitosn que empiecen por 6 o 9")
    @NotNull
    @Column(name = "telefono", length = 15)
    private String telefono;

    @Size(min = 1, max = 100,  message = "Minimo 1 letra y maximo 100")
    @NotNull
    @Column(name = "domicilio", length = 100)
    private String domicilio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}