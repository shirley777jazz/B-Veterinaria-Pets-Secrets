package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServicioResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;

    public ServicioResponse() {
    }

    public ServicioResponse(Integer id, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public static ServicioResponse from(com.PetsSecrets.Veterinaria_Backend.models.Servicio servicio) {
        if (servicio == null) return null;
        return ServicioResponse.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .descripcion(servicio.getDescripcion())
                .precio(servicio.getPrecio())
                .build();
    }
}