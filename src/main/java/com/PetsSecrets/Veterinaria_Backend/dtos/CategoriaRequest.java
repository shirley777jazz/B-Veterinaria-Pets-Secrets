package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaRequest {
    private String nombre;
    private String descripcion;

    public CategoriaRequest() {
    }

    public CategoriaRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}