package com.example.eval.controller;

// DTO para recibir el filtro de búsqueda
public class ItemSearchRequest {
    private String nombre;

    public ItemSearchRequest() {}

    public ItemSearchRequest(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
