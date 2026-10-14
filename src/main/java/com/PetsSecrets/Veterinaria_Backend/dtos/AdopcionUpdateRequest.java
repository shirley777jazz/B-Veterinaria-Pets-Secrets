package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Adopcion;

@Data
@Builder
public class AdopcionUpdateRequest {
    
    private Adopcion.EstadoAdopcion estado;
    private String motivoRechazo;
    private String observaciones;
    private String comentarioAdmin;

    public AdopcionUpdateRequest() {
    }

    public AdopcionUpdateRequest(Adopcion.EstadoAdopcion estado, String motivoRechazo, String observaciones, String comentarioAdmin) {
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
        this.observaciones = observaciones;
        this.comentarioAdmin = comentarioAdmin;
    }

    public Adopcion.EstadoAdopcion getEstado() {
        return estado;
    }

    public void setEstado(Adopcion.EstadoAdopcion estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getComentarioAdmin() {
        return comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin) {
        this.comentarioAdmin = comentarioAdmin;
    }
}