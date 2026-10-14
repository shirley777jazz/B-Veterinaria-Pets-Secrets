package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarritoItemRequest {
    private Integer productoId;
    private Integer cantidad;

    public CarritoItemRequest() {
    }

    public CarritoItemRequest(Integer productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}