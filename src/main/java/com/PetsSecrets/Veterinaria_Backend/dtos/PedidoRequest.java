package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PedidoRequest {
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String telefonoContacto;
    private String metodoPago;
    private LocalDate fechaEntrega;
    private BigDecimal envio;

    public PedidoRequest() {
    }

    public PedidoRequest(String direccion, String ciudad, String codigoPostal, String telefonoContacto, String metodoPago, LocalDate fechaEntrega, BigDecimal envio) {
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.telefonoContacto = telefonoContacto;
        this.metodoPago = metodoPago;
        this.fechaEntrega = fechaEntrega;
        this.envio = envio;
    }
}