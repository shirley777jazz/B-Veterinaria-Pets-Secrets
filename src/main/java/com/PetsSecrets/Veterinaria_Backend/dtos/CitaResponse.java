package com.PetsSecrets.Veterinaria_Backend.dtos;

import com.PetsSecrets.Veterinaria_Backend.models.Cita;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
public class CitaResponse {
    
    private Integer id;
    private Integer usuarioId;
    private String usuarioNombre;
    private String usuarioEmail;
    private String usuarioTelefono;
    
    private Integer mascotaId;
    private String mascotaNombre;
    private String mascotaEspecie;
    private String mascotaRaza;
    private String mascotaFotoUrl;
    
    private Integer servicioId;
    private String servicioNombre;
    private String servicioDescripcion;
    private Double servicioPrecio;
    
    private Integer sedeId;
    private String sedeNombre;
    private String sedeDireccion;
    private String sedeTelefono;
    
    private LocalDate fecha;
    private LocalTime hora;
    private Cita.EstadoCita estado;
    private String notas;
    private LocalDateTime fechaCreacion;

    public CitaResponse() {
    }

    public CitaResponse(Integer id, Integer usuarioId, String usuarioNombre, String usuarioEmail,
                        String usuarioTelefono, Integer mascotaId, String mascotaNombre,
                        String mascotaEspecie, String mascotaRaza, String mascotaFotoUrl,
                        Integer servicioId, String servicioNombre, String servicioDescripcion,
                        Double servicioPrecio, Integer sedeId, String sedeNombre,
                        String sedeDireccion, String sedeTelefono, LocalDate fecha,
                        LocalTime hora, Cita.EstadoCita estado, String notas,
                        LocalDateTime fechaCreacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
        this.usuarioTelefono = usuarioTelefono;
        this.mascotaId = mascotaId;
        this.mascotaNombre = mascotaNombre;
        this.mascotaEspecie = mascotaEspecie;
        this.mascotaRaza = mascotaRaza;
        this.mascotaFotoUrl = mascotaFotoUrl;
        this.servicioId = servicioId;
        this.servicioNombre = servicioNombre;
        this.servicioDescripcion = servicioDescripcion;
        this.servicioPrecio = servicioPrecio;
        this.sedeId = sedeId;
        this.sedeNombre = sedeNombre;
        this.sedeDireccion = sedeDireccion;
        this.sedeTelefono = sedeTelefono;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.notas = notas;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Constructor estático para crear desde entidad
    public static CitaResponse from(Cita cita) {
        if (cita == null) return null;
        return CitaResponse.builder()
                .id(cita.getId())
                .usuarioId(cita.getUsuario() != null ? cita.getUsuario().getId() : null)
                .usuarioNombre(cita.getUsuario() != null ? cita.getUsuario().getNombreCompleto() : null)
                .usuarioEmail(cita.getUsuario() != null ? cita.getUsuario().getEmail() : null)
                .usuarioTelefono(cita.getUsuario() != null ? cita.getUsuario().getTelefono() : null)
                .mascotaId(cita.getMascota() != null ? cita.getMascota().getId() : null)
                .mascotaNombre(cita.getMascota() != null ? cita.getMascota().getNombre() : null)
                .mascotaEspecie(cita.getMascota() != null && cita.getMascota().getEspecie() != null ? cita.getMascota().getEspecie().name() : null)
                .mascotaRaza(cita.getMascota() != null ? cita.getMascota().getRaza() : null)
                .mascotaFotoUrl(cita.getMascota() != null ? cita.getMascota().getFotoUrl() : null)
                .servicioId(cita.getServicio() != null ? cita.getServicio().getId() : null)
                .servicioNombre(cita.getServicio() != null ? cita.getServicio().getNombre() : null)
                .servicioDescripcion(cita.getServicio() != null ? cita.getServicio().getDescripcion() : null)
                .servicioPrecio(cita.getServicio() != null ? cita.getServicio().getPrecio() : null)
                .sedeId(cita.getSede() != null ? cita.getSede().getId() : null)
                .sedeNombre(cita.getSede() != null ? cita.getSede().getNombre() : null)
                .sedeDireccion(cita.getSede() != null ? cita.getSede().getDireccion() : null)
                .sedeTelefono(cita.getSede() != null ? cita.getSede().getTelefono() : null)
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .estado(cita.getEstado())
                .notas(cita.getNotas())
                .fechaCreacion(cita.getFechaCreacion())
                .build();
    }
}