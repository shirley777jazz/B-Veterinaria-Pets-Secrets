package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarritoItemResponse {
    private Integer id;
    private Integer productoId;
    private String productoNombre;
    private String productoImagenUrl;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public CarritoItemResponse() {
    }

    public CarritoItemResponse(Integer id, Integer productoId, String productoNombre, String productoImagenUrl, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.productoImagenUrl = productoImagenUrl;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
}