package com.PetsSecrets.Veterinaria_Backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @Builder.Default
    private Integer stock = 0;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoProducto estado = EstadoProducto.activo;

    @Column(name = "fecha_creacion")
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonBackReference
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PedidoDetalle> PedidosDetalles;

    public Producto() {
    }

    public Producto(Integer id, String nombre, String descripcion, double precio,
                    Integer stock, String imagenUrl, EstadoProducto estado,
                    LocalDateTime fechaCreacion, Categoria categoria,
                    List<PedidoDetalle> PedidosDetalles) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock != null ? stock : 0;
        this.imagenUrl = imagenUrl;
        this.estado = estado != null ? estado : EstadoProducto.activo;
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : LocalDateTime.now();
        this.categoria = categoria;
        this.PedidosDetalles = PedidosDetalles;
    }

    public enum EstadoProducto {
        activo,
        inactivo
    }
}
