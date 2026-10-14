package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CitaRequest {
    
    @NotNull(message = "La mascota es obligatoria")
    private Integer mascotaId;
    
    @NotNull(message = "El servicio es obligatorio")
    private Integer servicioId;
    
    @NotNull(message = "La sede es obligatoria")
    private Integer sedeId;
    
    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    @JsonFormat(pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime hora;
    
    private String notas;

    public CitaRequest() {
    }

    public CitaRequest(Integer mascotaId, Integer servicioId, Integer sedeId, LocalDate fecha, LocalTime hora, String notas) {
        this.mascotaId = mascotaId;
        this.servicioId = servicioId;
        this.sedeId = sedeId;
        this.fecha = fecha;
        this.hora = hora;
        this.notas = notas;
    }
}