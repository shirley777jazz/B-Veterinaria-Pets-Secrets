package com.PetsSecrets.Veterinaria_Backend.services;

import com.PetsSecrets.Veterinaria_Backend.dtos.PedidoDetalleResponse;
import com.PetsSecrets.Veterinaria_Backend.dtos.PedidoRequest;
import com.PetsSecrets.Veterinaria_Backend.dtos.PedidoResponse;
import com.PetsSecrets.Veterinaria_Backend.models.*;
import com.PetsSecrets.Veterinaria_Backend.repositories.PedidoRepository;
import com.PetsSecrets.Veterinaria_Backend.repositories.PedidoDetalleRepository;
import com.PetsSecrets.Veterinaria_Backend.repositories.ProductoRepository;
import com.PetsSecrets.Veterinaria_Backend.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoDetalleRepository pedidoDetalleRepository,
                         UsuarioRepository usuarioRepository,
                         CarritoService carritoService,
                         ProductoService productoService,
                         ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.usuarioRepository = usuarioRepository;
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    public List<PedidoResponse> obtenerTodosLosPedidos() {
        return pedidoRepository.findAllWithDetalles().stream()
                .map(this::convertirAPedidoResponse)
                .collect(Collectors.toList());
    }

    public List<PedidoResponse> obtenerPedidosPorUsuario(Integer usuarioId) {
        return pedidoRepository.findByUsuarioIdWithDetalles(usuarioId).stream()
                .map(this::convertirAPedidoResponse)
                .collect(Collectors.toList());
    }

    public PedidoResponse obtenerPedidoPorId(Integer id) {
        Pedido pedido = pedidoRepository.findByIdWithDetalles(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return convertirAPedidoResponse(pedido);
    }

    public PedidoResponse obtenerPedidoPorCodigo(String codigo) {
        Pedido pedido = pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con código: " + codigo));
        return convertirAPedidoResponse(pedido);
    }

    @Transactional
    public PedidoResponse crearPedidoDesdeCarrito(Integer usuarioId, PedidoRequest request) {
        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener carrito con items
        Carrito carrito = carritoService.obtenerCarritoPorUsuarioInterno(usuarioId);
        
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Verificar stock disponible para todos los productos directamente desde la BD
        for (CarritoItem item : carrito.getItems()) {
            Producto prodDB = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProducto().getNombre()));
            if (prodDB.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + prodDB.getNombre() + 
                                         ". Stock disponible: " + prodDB.getStock() + 
                                         ", cantidad solicitada: " + item.getCantidad());
            }
        }

        // Calcular totales
        BigDecimal subtotal = carrito.getItems().stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal envio = request.getEnvio() != null ? request.getEnvio() : BigDecimal.ZERO;
        BigDecimal total = subtotal.add(envio);

        // Crear pedido
        Pedido pedido = Pedido.builder()
                .codigo(generarCodigoPedido())
                .usuario(usuario)
                .fechaPedido(LocalDateTime.now())
                .estado(Pedido.EstadoPedido.pendiente)
                .subtotal(subtotal)
                .envio(envio)
                .total(total)
                .direccion(request.getDireccion())
                .ciudad(request.getCiudad())
                .codigoPostal(request.getCodigoPostal())
                .telefonoContacto(request.getTelefonoContacto())
                .metodoPago(Pedido.MetodoPago.valueOf(request.getMetodoPago()))
                .fechaEntrega(request.getFechaEntrega())
                .build();

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Crear detalles del pedido y descontar stock guardando en BD
        List<PedidoDetalle> detallesList = new ArrayList<>();
        for (CarritoItem item : carrito.getItems()) {
            PedidoDetalle detalle = PedidoDetalle.builder()
                    .pedido(pedidoGuardado)
                    .producto(item.getProducto())
                    .cantidad(item.getCantidad())
                    .precioUnitario(item.getPrecioUnitario())
                    .subtotal(item.getSubtotal())
                    .build();

            PedidoDetalle detalleGuardado = pedidoDetalleRepository.save(detalle);
            detallesList.add(detalleGuardado);

            // Actualizar y guardar nuevo stock del producto en la base de datos
            Producto producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getProducto().getId()));
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }
        
        // Asociar los detalles al pedido
        pedidoGuardado.setDetalles(detallesList);

        // Limpiar carrito
        carritoService.vaciarCarrito(usuarioId);

        return convertirAPedidoResponse(pedidoRepository.findByIdWithDetalles(pedidoGuardado.getId()).get());
    }

    public PedidoResponse actualizarEstadoPedido(Integer id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findByIdWithDetalles(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        try {
            Pedido.EstadoPedido estado = null;
            for (Pedido.EstadoPedido ep : Pedido.EstadoPedido.values()) {
                if (ep.name().equalsIgnoreCase(nuevoEstado != null ? nuevoEstado.trim() : "")) {
                    estado = ep;
                    break;
                }
            }
            if (estado == null) {
                throw new RuntimeException("Estado de pedido inválido: " + nuevoEstado);
            }

            Pedido.EstadoPedido estadoAnterior = pedido.getEstado();

            // Si se cancela el pedido, restablecer el stock de los productos
            if (estadoAnterior != Pedido.EstadoPedido.cancelado && estado == Pedido.EstadoPedido.cancelado) {
                restaurarStockPedido(pedido);
            } else if (estadoAnterior == Pedido.EstadoPedido.cancelado && estado != Pedido.EstadoPedido.cancelado) {
                // Si se reactiva un pedido cancelado, volver a descontar el stock
                descontarStockPedido(pedido);
            }

            pedido.setEstado(estado);
            Pedido pedidoActualizado = pedidoRepository.save(pedido);
            return convertirAPedidoResponse(pedidoActualizado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de pedido inválido: " + nuevoEstado);
        }
    }

    public List<PedidoResponse> obtenerPedidosPorEstado(String estado) {
        try {
            Pedido.EstadoPedido estadoEnum = null;
            for (Pedido.EstadoPedido ep : Pedido.EstadoPedido.values()) {
                if (ep.name().equalsIgnoreCase(estado != null ? estado.trim() : "")) {
                    estadoEnum = ep;
                    break;
                }
            }
            if (estadoEnum == null) {
                throw new RuntimeException("Estado de pedido inválido: " + estado);
            }
            return pedidoRepository.findByEstado(estadoEnum).stream()
                    .map(this::convertirAPedidoResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de pedido inválido: " + estado);
        }
    }

    public PedidoResponse actualizarFechaEntrega(Integer id, String fechaEntrega) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        try {
            // Convertir la fecha string a LocalDate
            java.time.LocalDate fechaEntregaDate = java.time.LocalDate.parse(fechaEntrega);
            pedido.setFechaEntrega(fechaEntregaDate);
            Pedido pedidoActualizado = pedidoRepository.save(pedido);
            return convertirAPedidoResponse(pedidoActualizado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar fecha de entrega: " + e.getMessage());
        }
    }

    public PedidoResponse cancelarPedido(Integer id, Integer usuarioId) {
        Pedido pedido = pedidoRepository.findByIdWithDetalles(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        if (!pedido.getUsuario().getId().equals(usuarioId)) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            if (usuario.getRol() != Usuario.Rol.admin) {
                throw new RuntimeException("No tienes permisos para cancelar este pedido");
            }
        }

        if (pedido.getEstado() != Pedido.EstadoPedido.pendiente) {
            throw new RuntimeException("Solo se pueden cancelar pedidos en estado pendiente");
        }

        // Restablecer stock de los productos comprados si no estaba cancelado
        if (pedido.getEstado() != Pedido.EstadoPedido.cancelado) {
            restaurarStockPedido(pedido);
        }

        pedido.setEstado(Pedido.EstadoPedido.cancelado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return convertirAPedidoResponse(pedidoActualizado);
    }

    /**
     * Restablece el stock de cada producto involucrado en el pedido cuando es cancelado.
     */
    private void restaurarStockPedido(Pedido pedido) {
        if (pedido.getDetalles() != null) {
            for (PedidoDetalle detalle : pedido.getDetalles()) {
                if (detalle.getProducto() != null) {
                    Producto prod = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
                    if (prod != null) {
                        prod.setStock(prod.getStock() + detalle.getCantidad());
                        productoRepository.save(prod);
                    }
                }
            }
        }
    }

    /**
     * Descuenta el stock si un pedido cancelado es reactivado.
     */
    private void descontarStockPedido(Pedido pedido) {
        if (pedido.getDetalles() != null) {
            for (PedidoDetalle detalle : pedido.getDetalles()) {
                if (detalle.getProducto() != null) {
                    Producto prod = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
                    if (prod != null) {
                        if (prod.getStock() < detalle.getCantidad()) {
                            throw new RuntimeException("Stock insuficiente para reactivar el pedido del producto: " + prod.getNombre());
                        }
                        prod.setStock(prod.getStock() - detalle.getCantidad());
                        productoRepository.save(prod);
                    }
                }
            }
        }
    }

    private String generarCodigoPedido() {
        String codigo;
        do {
            codigo = "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (pedidoRepository.findByCodigo(codigo).isPresent());
        return codigo;
    }

    private PedidoResponse convertirAPedidoResponse(Pedido pedido) {
        List<PedidoDetalleResponse> detalles = pedido.getDetalles() != null 
                ? pedido.getDetalles().stream()
                    .map(this::convertirAPedidoDetalleResponse)
                    .collect(Collectors.toList())
                : new ArrayList<>();

        return PedidoResponse.builder()
                .id(pedido.getId())
                .codigo(pedido.getCodigo())
                .usuarioId(pedido.getUsuario().getId())
                .usuarioNombre(pedido.getUsuario().getNombreCompleto())
                .usuarioEmail(pedido.getUsuario().getEmail())
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado().toString())
                .subtotal(pedido.getSubtotal())
                .envio(pedido.getEnvio())
                .total(pedido.getTotal())
                .direccion(pedido.getDireccion())
                .ciudad(pedido.getCiudad())
                .codigoPostal(pedido.getCodigoPostal())
                .telefonoContacto(pedido.getTelefonoContacto())
                .metodoPago(pedido.getMetodoPago().toString())
                .fechaEntrega(pedido.getFechaEntrega())
                .detalles(detalles)
                .build();
    }

    private PedidoDetalleResponse convertirAPedidoDetalleResponse(PedidoDetalle detalle) {
        return PedidoDetalleResponse.builder()
                .id(detalle.getId())
                .productoId(detalle.getProducto().getId())
                .productoNombre(detalle.getProducto().getNombre())
                .productoImagenUrl(detalle.getProducto().getImagenUrl())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
