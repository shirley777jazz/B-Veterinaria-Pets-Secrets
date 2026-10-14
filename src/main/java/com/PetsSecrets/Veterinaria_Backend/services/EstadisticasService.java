package com.PetsSecrets.Veterinaria_Backend.services;

import com.PetsSecrets.Veterinaria_Backend.dtos.EstadisticasResponse;
import com.PetsSecrets.Veterinaria_Backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PetsSecrets.Veterinaria_Backend.models.Mascota;
import com.PetsSecrets.Veterinaria_Backend.models.Pedido;
import com.PetsSecrets.Veterinaria_Backend.models.Adopcion;

import java.time.LocalDate;

@Service
public class EstadisticasService {
    
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final AdopcionRepository adopcionRepository;
    private final CitaRepository citaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public EstadisticasService(UsuarioRepository usuarioRepository,
                              MascotaRepository mascotaRepository,
                              AdopcionRepository adopcionRepository,
                              CitaRepository citaRepository,
                              PedidoRepository pedidoRepository,
                              ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.adopcionRepository = adopcionRepository;
        this.citaRepository = citaRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }
    
    public EstadisticasResponse obtenerEstadisticasDashboard() {
        LocalDate hoy = LocalDate.now();
        
        Long usuarios = safeCount(usuarioRepository::count);
        Long mascotas = safeCount(() -> mascotaRepository.countByEstadoAdopcion(Mascota.EstadoAdopcion.disponible));
        Long pedidosPend = safeCount(() -> pedidoRepository.countByEstado(Pedido.EstadoPedido.pendiente));
        Long citasH = safeCount(() -> citaRepository.countByFecha(hoy));
        Long productosStk = safeCount(() -> productoRepository.countByStockGreaterThan(0));
        Double valorInv = calcularValorInventario();
        Long totalAdop = safeCount(() -> adopcionRepository.countByEstado(Adopcion.EstadoAdopcion.aprobada));
        Long citasSem = safeCount(() -> citaRepository.countCitasUltimaSemana(hoy.minusWeeks(1)));
        Long citasM = safeCount(() -> citaRepository.countCitasUltimoMes(hoy.minusMonths(1)));
        Long pedidosComp = safeCount(() -> pedidoRepository.countByEstado(Pedido.EstadoPedido.entregado));
        Long prodVend = calcularProductosVendidos();
        Double ingrTot = calcularIngresosTotales();

        return EstadisticasResponse.builder()
                .usuariosRegistrados(usuarios)
                .mascotasAdopcion(mascotas)
                .pedidosPendientes(pedidosPend)
                .citasHoy(citasH)
                .productosStock(productosStk)
                .valorInventario(valorInv)
                .totalAdopciones(totalAdop)
                .citasSemana(citasSem)
                .citasMes(citasM)
                .pedidosCompletados(pedidosComp)
                .productosVendidos(prodVend)
                .ingresosTotales(ingrTot)
                .build();
    }
    
    public EstadisticasResponse obtenerReporte(String fechaInicio, String fechaFin) {
        LocalDate hoy = LocalDate.now();
        
        LocalDate inicio = fechaInicio != null ? LocalDate.parse(fechaInicio) : hoy.minusMonths(1);
        LocalDate fin = fechaFin != null ? LocalDate.parse(fechaFin) : hoy;
        
        Long usuarios = safeCount(usuarioRepository::count);
        Long mascotas = safeCount(() -> mascotaRepository.countByEstadoAdopcion(Mascota.EstadoAdopcion.disponible));
        Long pedidosPend = safeCount(() -> pedidoRepository.countByEstado(Pedido.EstadoPedido.pendiente));
        Long citasH = safeCount(() -> citaRepository.countByFecha(hoy));
        Long productosStk = safeCount(() -> productoRepository.countByStockGreaterThan(0));
        Double valorInv = calcularValorInventario();
        Long totalAdop = safeCount(() -> adopcionRepository.countByEstado(Adopcion.EstadoAdopcion.aprobada));
        Long citasSem = safeCount(() -> citaRepository.countCitasEntreFechas(inicio, fin));
        Long citasM = safeCount(() -> citaRepository.countCitasEntreFechas(inicio, fin));
        Long pedidosComp = safeCount(() -> pedidoRepository.countByEstado(Pedido.EstadoPedido.entregado));
        Long prodVend = calcularProductosVendidos();
        Double ingrTot = calcularIngresosTotales();

        return EstadisticasResponse.builder()
                .usuariosRegistrados(usuarios)
                .mascotasAdopcion(mascotas)
                .pedidosPendientes(pedidosPend)
                .citasHoy(citasH)
                .productosStock(productosStk)
                .valorInventario(valorInv)
                .totalAdopciones(totalAdop)
                .citasSemana(citasSem)
                .citasMes(citasM)
                .pedidosCompletados(pedidosComp)
                .productosVendidos(prodVend)
                .ingresosTotales(ingrTot)
                .periodoInicio(inicio.toString())
                .periodoFin(fin.toString())
                .build();
    }

    private Long safeCount(java.util.function.Supplier<Long> supplier) {
        try {
            Long val = supplier.get();
            return val != null ? val : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    private Double calcularValorInventario() {
        try {
            return productoRepository.findAll().stream()
                    .mapToDouble(producto -> {
                        int stock = producto.getStock() != null ? producto.getStock() : 0;
                        double precio = producto.getPrecio() > 0 ? producto.getPrecio() : 0.0;
                        return stock * precio;
                    })
                    .sum();
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private Long calcularProductosVendidos() {
        try {
            Long total = pedidoRepository.countProductosVendidos(Pedido.EstadoPedido.entregado);
            return total != null ? total : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }
    
    private Double calcularIngresosTotales() {
        try {
            Double total = pedidoRepository.sumIngresosByEstado(Pedido.EstadoPedido.entregado);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}