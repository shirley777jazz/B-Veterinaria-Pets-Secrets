package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Adopcion;

@Data
@Builder
public class AdopcionRequest {
    
    private Integer mascotaId;
    private Adopcion.ExperienciaMascotas experienciaMascotas;
    private Adopcion.TipoVivienda tipoVivienda;
    private String otrasMascotas;
    private String horarioTrabajo;
    private String motivoAdopcion;
    private String contactoEmergencia;
    private String veterinarioReferencia;
    private Boolean aceptaCondiciones;
    private Boolean aceptaVisita;

    public AdopcionRequest() {
    }

    public AdopcionRequest(Integer mascotaId, Adopcion.ExperienciaMascotas experienciaMascotas,
                          Adopcion.TipoVivienda tipoVivienda, String otrasMascotas,
                          String horarioTrabajo, String motivoAdopcion,
                          String contactoEmergencia, String veterinarioReferencia,
                          Boolean aceptaCondiciones, Boolean aceptaVisita) {
        this.mascotaId = mascotaId;
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
}