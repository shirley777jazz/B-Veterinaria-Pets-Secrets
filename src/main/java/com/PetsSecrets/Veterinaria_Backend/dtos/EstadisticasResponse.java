package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadisticasResponse {
    
    // Estadísticas principales del dashboard
    private Long usuariosRegistrados;
    private Long mascotasAdopcion;
    private Long pedidosPendientes;
    private Long citasHoy;
    private Long productosStock;
    private Double valorInventario;
    
    // Estadísticas adicionales para reportes
    private Long totalAdopciones;
    private Long citasSemana;
    private Long citasMes;
    private Long pedidosCompletados;
    private Long productosVendidos;
    private Double ingresosTotales;
    
    // Estadísticas por período (para reportes con fechas)
    private String periodoInicio;
    private String periodoFin;

    public EstadisticasResponse() {
    }

    public EstadisticasResponse(Long usuariosRegistrados, Long mascotasAdopcion, Long pedidosPendientes, Long citasHoy, Long productosStock, Double valorInventario, Long totalAdopciones, Long citasSemana, Long citasMes, Long pedidosCompletados, Long productosVendidos, Double ingresosTotales, String periodoInicio, String periodoFin) {
        this.usuariosRegistrados = usuariosRegistrados;
        this.mascotasAdopcion = mascotasAdopcion;
        this.pedidosPendientes = pedidosPendientes;
        this.citasHoy = citasHoy;
        this.productosStock = productosStock;
        this.valorInventario = valorInventario;
        this.totalAdopciones = totalAdopciones;
        this.citasSemana = citasSemana;
        this.citasMes = citasMes;
        this.pedidosCompletados = pedidosCompletados;
        this.productosVendidos = productosVendidos;
        this.ingresosTotales = ingresosTotales;
        this.periodoInicio = periodoInicio;
        this.periodoFin = periodoFin;
    }
}