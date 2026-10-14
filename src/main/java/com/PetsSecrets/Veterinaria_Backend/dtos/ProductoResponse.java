package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductoResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer stock;
    private String imagenUrl;
    private String estado;
    private LocalDateTime fechaCreacion;
    private CategoriaResponse categoria;

    public ProductoResponse() {
    }

    public ProductoResponse(Integer id, String nombre, String descripcion, double precio, Integer stock, String imagenUrl, String estado, LocalDateTime fechaCreacion, CategoriaResponse categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.categoria = categoria;
    }
}