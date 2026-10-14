package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {
    private Integer id;
    private String codigo;
    private Integer usuarioId;
    private String usuarioNombre;
    private String usuarioEmail;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal envio;
    private BigDecimal total;
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String telefonoContacto;
    private String metodoPago;
    private LocalDate fechaEntrega;
    private List<PedidoDetalleResponse> detalles;

    public PedidoResponse() {
    }

    public PedidoResponse(Integer id, String codigo, Integer usuarioId, String usuarioNombre, String usuarioEmail, LocalDateTime fechaPedido, String estado, BigDecimal subtotal, BigDecimal envio, BigDecimal total, String direccion, String ciudad, String codigoPostal, String telefonoContacto, String metodoPago, LocalDate fechaEntrega, List<PedidoDetalleResponse> detalles) {
        this.id = id;
        this.codigo = codigo;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.subtotal = subtotal;
        this.envio = envio;
        this.total = total;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.telefonoContacto = telefonoContacto;
        this.metodoPago = metodoPago;
        this.fechaEntrega = fechaEntrega;
        this.detalles = detalles;
    }
}