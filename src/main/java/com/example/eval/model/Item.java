package com.example.eval.model;

import jakarta.persistence.*;

// Entidad JPA que representa la tabla ITEM en H2
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private Double precio;

    public Item() {}

    public Item(String nombre, String categoria, Double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
