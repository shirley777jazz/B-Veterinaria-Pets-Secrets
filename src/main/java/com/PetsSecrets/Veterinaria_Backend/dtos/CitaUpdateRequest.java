package com.PetsSecrets.Veterinaria_Backend.dtos;

import com.PetsSecrets.Veterinaria_Backend.models.Cita;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CitaUpdateRequest {
    
    @NotNull(message = "El estado es obligatorio")
    private Cita.EstadoCita estado;
    
    private LocalDate fecha;
    
    private LocalTime hora;
    
    private String notas;
    
    private String comentarioAdmin;

    public CitaUpdateRequest() {
    }

    public CitaUpdateRequest(Cita.EstadoCita estado, LocalDate fecha, LocalTime hora, String notas, String comentarioAdmin) {
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.notas = notas;
        this.comentarioAdmin = comentarioAdmin;
    }
}