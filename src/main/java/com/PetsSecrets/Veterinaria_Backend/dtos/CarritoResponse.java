package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CarritoResponse {
    private Integer id;
    private Integer usuarioId;
    private LocalDateTime fechaCreacion;
    private List<CarritoItemResponse> items;
    private BigDecimal total;
    private Integer totalItems;

    public CarritoResponse() {
    }

    public CarritoResponse(Integer id, Integer usuarioId, LocalDateTime fechaCreacion, List<CarritoItemResponse> items, BigDecimal total, Integer totalItems) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaCreacion = fechaCreacion;
        this.items = items;
        this.total = total;
        this.totalItems = totalItems;
    }
}