package com.proyecto.api_rest_tiendaonline.modelos;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min=1, max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo son admitidas letras y numeros")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    @NotNull
    private String descripcion;

    @NotNull
    @PositiveOrZero
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;


    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }


}