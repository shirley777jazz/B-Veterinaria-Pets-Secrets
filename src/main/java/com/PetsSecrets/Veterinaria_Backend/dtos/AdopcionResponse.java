package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Adopcion;

import java.time.LocalDateTime;

@Data
@Builder
public class AdopcionResponse {
    
    private Integer id;
    private Integer usuarioId;
    private String usuarioNombre;
    private String usuarioEmail;
    private Integer mascotaId;
    private String mascotaNombre;
    private String mascotaEspecie;
    private String mascotaRaza;
    private String mascotaFotoUrl;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaAprobacion;
    private Adopcion.EstadoAdopcion estado;
    private Adopcion.ExperienciaMascotas experienciaMascotas;
    private Adopcion.TipoVivienda tipoVivienda;
    private String otrasMascotas;
    private String horarioTrabajo;
    private String motivoAdopcion;
    private String contactoEmergencia;
    private String veterinarioReferencia;
    private Boolean aceptaCondiciones;
    private Boolean aceptaVisita;

    public AdopcionResponse() {
    }

    public AdopcionResponse(Integer id, Integer usuarioId, String usuarioNombre, String usuarioEmail,
                            Integer mascotaId, String mascotaNombre, String mascotaEspecie,
                            String mascotaRaza, String mascotaFotoUrl, LocalDateTime fechaSolicitud,
                            LocalDateTime fechaAprobacion, Adopcion.EstadoAdopcion estado,
                            Adopcion.ExperienciaMascotas experienciaMascotas, Adopcion.TipoVivienda tipoVivienda,
                            String otrasMascotas, String horarioTrabajo, String motivoAdopcion,
                            String contactoEmergencia, String veterinarioReferencia,
                            Boolean aceptaCondiciones, Boolean aceptaVisita) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
        this.mascotaId = mascotaId;
        this.mascotaNombre = mascotaNombre;
        this.mascotaEspecie = mascotaEspecie;
        this.mascotaRaza = mascotaRaza;
        this.mascotaFotoUrl = mascotaFotoUrl;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.estado = estado;
        this.experienciaMascotas = experienciaMascotas;
        this.tipoVivienda = tipoVivienda;
        this.otrasMascotas = otrasMascotas;
        this.horarioTrabajo = horarioTrabajo;
        this.motivoAdopcion = motivoAdopcion;
        this.contactoEmergencia = contactoEmergencia;
        this.veterinarioReferencia = veterinarioReferencia;
        this.aceptaCondiciones = aceptaCondiciones;
        this.aceptaVisita = aceptaVisita;
    }
    
    // Constructor estático para crear desde entidad
    public static AdopcionResponse from(Adopcion adopcion) {
        if (adopcion == null) return null;
        return AdopcionResponse.builder()
                .id(adopcion.getId())
                .usuarioId(adopcion.getUsuario() != null ? adopcion.getUsuario().getId() : null)
                .usuarioNombre(adopcion.getUsuario() != null ? adopcion.getUsuario().getNombreCompleto() : null)
                .usuarioEmail(adopcion.getUsuario() != null ? adopcion.getUsuario().getEmail() : null)
                .mascotaId(adopcion.getMascota() != null ? adopcion.getMascota().getId() : null)
                .mascotaNombre(adopcion.getMascota() != null ? adopcion.getMascota().getNombre() : null)
                .mascotaEspecie(adopcion.getMascota() != null && adopcion.getMascota().getEspecie() != null ? adopcion.getMascota().getEspecie().name() : null)
                .mascotaRaza(adopcion.getMascota() != null ? adopcion.getMascota().getRaza() : null)
                .mascotaFotoUrl(adopcion.getMascota() != null ? adopcion.getMascota().getFotoUrl() : null)
                .fechaSolicitud(adopcion.getFechaSolicitud())
                .fechaAprobacion(adopcion.getFechaAprobacion())
                .estado(adopcion.getEstado())
                .experienciaMascotas(adopcion.getExperienciaMascotas())
                .tipoVivienda(adopcion.getTipoVivienda())
                .otrasMascotas(adopcion.getOtrasMascotas())
                .horarioTrabajo(adopcion.getHorarioTrabajo())
                .motivoAdopcion(adopcion.getMotivoAdopcion())
                .contactoEmergencia(adopcion.getContactoEmergencia())
                .veterinarioReferencia(adopcion.getVeterinarioReferencia())
                .aceptaCondiciones(adopcion.getAceptaCondiciones())
                .aceptaVisita(adopcion.getAceptaVisita())
                .build();
    }
}