package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Mascota;

@Data
@Builder
public class MascotaRequest {
    
    private String nombre;
    private Mascota.Especie especie;
    private String raza;
    private Mascota.Genero genero;
    private Integer edad;
    private Mascota.Tamano tamano;
    private String descripcion;
    private String fotoUrl;
    private Mascota.Tipo tipo;
    private Boolean vacunado;
    private Boolean esterilizado;
    private Boolean buenoConNinos;
    private Boolean buenoConOtrasMascotas;
    private String motivoAdopcion;

    public MascotaRequest() {
    }

    public MascotaRequest(String nombre, Mascota.Especie especie, String raza,
                          Mascota.Genero genero, Integer edad, Mascota.Tamano tamano,
                          String descripcion, String fotoUrl, Mascota.Tipo tipo,
                          Boolean vacunado, Boolean esterilizado, Boolean buenoConNinos,
                          Boolean buenoConOtrasMascotas, String motivoAdopcion) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.edad = edad;
        this.tamano = tamano;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
        this.tipo = tipo;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        this.buenoConNinos = buenoConNinos;
        this.buenoConOtrasMascotas = buenoConOtrasMascotas;
        this.motivoAdopcion = motivoAdopcion;
    }
}