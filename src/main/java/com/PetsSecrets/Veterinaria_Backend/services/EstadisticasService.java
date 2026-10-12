package com.PetsSecrets.Veterinaria_Backend.services;

import com.PetsSecrets.Veterinaria_Backend.dtos.EstadisticasResponse;
import com.PetsSecrets.Veterinaria_Backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.PetsSecrets.Veterinaria_Backend.models.Mascota;
import com.PetsSecrets.Veterinaria_Backend.models.Pedido;
import com.PetsSecrets.Veterinaria_Backend.models.Adopcion;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EstadisticasService {
    
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final AdopcionRepository adopcionRepository;
    private final CitaRepository citaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    
    public EstadisticasResponse obtenerEstadisticasDashboard() {
        LocalDate hoy = LocalDate.now();
        
        return EstadisticasResponse.builder()
                .usuariosRegistrados(usuarioRepository.count())
                .mascotasAdopcion(mascotaRepository.countByEstadoAdopcion(Mascota.EstadoAdopcion.disponible))
                .pedidosPendientes(pedidoRepository.countByEstado(Pedido.EstadoPedido.pendiente))
                .citasHoy(citaRepository.countByFecha(hoy))
                .productosStock(productoRepository.countByStockGreaterThan(0))
                .valorInventario(calcularValorInventario())
                .totalAdopciones(adopcionRepository.countByEstado(Adopcion.EstadoAdopcion.aprobada))
                .citasSemana(citaRepository.countCitasUltimaSemana(hoy.minusWeeks(1)))
                .citasMes(citaRepository.countCitasUltimoMes(hoy.minusMonths(1)))
                .pedidosCompletados(pedidoRepository.countByEstado(Pedido.EstadoPedido.entregado))
                .productosVendidos(calcularProductosVendidos())
                .ingresosTotales(calcularIngresosTotales())
                .build();
    }
    
    public EstadisticasResponse obtenerReporte(String fechaInicio, String fechaFin) {
        LocalDate hoy = LocalDate.now();
        
        // Si no se proporcionan fechas, usar valores por defecto
        LocalDate inicio = fechaInicio != null ? LocalDate.parse(fechaInicio) : hoy.minusMonths(1);
        LocalDate fin = fechaFin != null ? LocalDate.parse(fechaFin) : hoy;
        
        return EstadisticasResponse.builder()
                .usuariosRegistrados(usuarioRepository.count())
                .mascotasAdopcion(mascotaRepository.countByEstadoAdopcion(Mascota.EstadoAdopcion.disponible))
                .pedidosPendientes(pedidoRepository.countByEstado(Pedido.EstadoPedido.pendiente))
                .citasHoy(citaRepository.countByFecha(hoy))
                .productosStock(productoRepository.countByStockGreaterThan(0))
                .valorInventario(calcularValorInventario())
                .totalAdopciones(adopcionRepository.countByEstado(Adopcion.EstadoAdopcion.aprobada))
                .citasSemana(citaRepository.countCitasEntreFechas(inicio, fin))
                .citasMes(citaRepository.countCitasEntreFechas(inicio, fin))
                .pedidosCompletados(pedidoRepository.countByEstado(Pedido.EstadoPedido.entregado))
                .productosVendidos(calcularProductosVendidos())
                .ingresosTotales(calcularIngresosTotales())
                .build();
    }

    private Double calcularValorInventario() {
        // Calcular el valor total del inventario (stock * precio)
        return productoRepository.findAll().stream()
                .mapToDouble(producto -> producto.getStock() * producto.getPrecio())
                .sum();
    }
    
    private Long calcularProductosVendidos() {
        // Contar productos vendidos basado en pedidos completados
        return pedidoRepository.countProductosVendidos(Pedido.EstadoPedido.entregado);
    }
    
    private Double calcularIngresosTotales() {
        // Sumar el total de pedidos completados
        return pedidoRepository.sumIngresosByEstado(Pedido.EstadoPedido.entregado);
    }
}