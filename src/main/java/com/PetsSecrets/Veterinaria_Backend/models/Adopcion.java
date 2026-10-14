package com.PetsSecrets.Veterinaria_Backend.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "adopciones")
@Data
@Builder
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @Column(name = "fecha_solicitud", nullable = false)
    @Builder.Default
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoAdopcion estado = EstadoAdopcion.pendiente;

    @Enumerated(EnumType.STRING)
    @Column(name = "experiencia_mascotas", nullable = false)
    private ExperienciaMascotas experienciaMascotas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vivienda", nullable = false)
    private TipoVivienda tipoVivienda;

    @Column(name = "otras_mascotas")
    private String otrasMascotas;

    @Column(name = "horario_trabajo")
    private String horarioTrabajo;

    @Column(name = "motivo_adopcion")
    private String motivoAdopcion;

    @Column(name = "contacto_emergencia", nullable = false)
    private String contactoEmergencia;

    @Column(name = "veterinario_referencia")
    private String veterinarioReferencia;

    @Column(name = "acepta_condiciones")
    @Builder.Default
    private Boolean aceptaCondiciones = false;

    @Column(name = "acepta_visita")
    @Builder.Default
    private Boolean aceptaVisita = false;

    public Adopcion() {
    }

    public Adopcion(Integer id, Usuario usuario, Mascota mascota, LocalDateTime fechaSolicitud,
                    LocalDateTime fechaAprobacion, EstadoAdopcion estado,
                    ExperienciaMascotas experienciaMascotas, TipoVivienda tipoVivienda,
                    String otrasMascotas, String horarioTrabajo, String motivoAdopcion,
                    String contactoEmergencia, String veterinarioReferencia,
                    Boolean aceptaCondiciones, Boolean aceptaVisita) {
        this.id = id;
        this.usuario = usuario;
        this.mascota = mascota;
        this.fechaSolicitud = fechaSolicitud != null ? fechaSolicitud : LocalDateTime.now();
        this.fechaAprobacion = fechaAprobacion;
        this.estado = estado != null ? estado : EstadoAdopcion.pendiente;
        this.experienciaMascotas = experienciaMascotas;
        this.tipoVivienda = tipoVivienda;
        this.otrasMascotas = otrasMascotas;
        this.horarioTrabajo = horarioTrabajo;
        this.motivoAdopcion = motivoAdopcion;
        this.contactoEmergencia = contactoEmergencia;
        this.veterinarioReferencia = veterinarioReferencia;
        this.aceptaCondiciones = aceptaCondiciones != null ? aceptaCondiciones : false;
        this.aceptaVisita = aceptaVisita != null ? aceptaVisita : false;
    }

    public enum EstadoAdopcion { pendiente,aprobada,rechazada,completada }
    public enum ExperienciaMascotas { nula,básica,intermedia,avanzada}
    public enum TipoVivienda { casa,departamento,finca,otro }
}
