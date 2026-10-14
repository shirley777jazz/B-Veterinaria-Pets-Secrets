package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SedeResponse {
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;

    public SedeResponse() {
    }

    public SedeResponse(Integer id, String nombre, String direccion, String telefono, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public static SedeResponse from(com.PetsSecrets.Veterinaria_Backend.models.Sede sede) {
        if (sede == null) return null;
        return SedeResponse.builder()
                .id(sede.getId())
                .nombre(sede.getNombre())
                .direccion(sede.getDireccion())
                .telefono(sede.getTelefono())
                .ciudad(sede.getCiudad())
                .build();
    }
}