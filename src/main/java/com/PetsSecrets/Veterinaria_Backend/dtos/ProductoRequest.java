package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer stock;
    private String imagenUrl;
    private String estado;
    private Integer categoriaId;

    public ProductoRequest() {
    }

    public ProductoRequest(String nombre, String descripcion, double precio, Integer stock, String imagenUrl, String estado, Integer categoriaId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.estado = estado;
        this.categoriaId = categoriaId;
    }
}