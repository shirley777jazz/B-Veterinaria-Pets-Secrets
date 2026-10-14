package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Builder;
import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Mascota;

import java.time.LocalDate;

@Data
@Builder
public class MascotaResponse {
    
    private Integer id;
    private String nombre;
    private Mascota.Especie especie;
    private String raza;
    private Mascota.Genero genero;
    private Integer edad;
    private Mascota.Tamano tamano;
    private String descripcion;
    private String fotoUrl;
    private Mascota.Tipo tipo;
    private Mascota.EstadoAdopcion estadoAdopcion;
    private Boolean vacunado;
    private Boolean esterilizado;
    private Boolean buenoConNinos;
    private Boolean buenoConOtrasMascotas;
    private LocalDate fechaIngreso;
    private LocalDate fechaAdopcion;
    private String usuarioNombre;
    private Integer usuarioId;

    public MascotaResponse() {
    }

    public MascotaResponse(Integer id, String nombre, Mascota.Especie especie, String raza,
                           Mascota.Genero genero, Integer edad, Mascota.Tamano tamano,
                           String descripcion, String fotoUrl, Mascota.Tipo tipo,
                           Mascota.EstadoAdopcion estadoAdopcion, Boolean vacunado,
                           Boolean esterilizado, Boolean buenoConNinos,
                           Boolean buenoConOtrasMascotas, LocalDate fechaIngreso,
                           LocalDate fechaAdopcion, String usuarioNombre, Integer usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.edad = edad;
        this.tamano = tamano;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
        this.tipo = tipo;
        this.estadoAdopcion = estadoAdopcion;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        this.buenoConNinos = buenoConNinos;
        this.buenoConOtrasMascotas = buenoConOtrasMascotas;
        this.fechaIngreso = fechaIngreso;
        this.fechaAdopcion = fechaAdopcion;
        this.usuarioNombre = usuarioNombre;
        this.usuarioId = usuarioId;
    }
    
    // Constructor estático para crear desde entidad
    public static MascotaResponse from(Mascota mascota) {
        if (mascota == null) return null;
        return MascotaResponse.builder()
                .id(mascota.getId())
                .nombre(mascota.getNombre())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                .genero(mascota.getGenero())
                .edad(mascota.getEdad())
                .tamano(mascota.getTamano())
                .descripcion(mascota.getDescripcion())
                .fotoUrl(mascota.getFotoUrl())
                .tipo(mascota.getTipo())
                .estadoAdopcion(mascota.getEstadoAdopcion())
                .vacunado(mascota.getVacunado())
                .esterilizado(mascota.getEsterilizado())
                .buenoConNinos(mascota.getBuenoConNinos())
                .buenoConOtrasMascotas(mascota.getBuenoConOtrasMascotas())
                .fechaIngreso(mascota.getFechaIngreso())
                .fechaAdopcion(mascota.getFechaAdopcion())
                .usuarioNombre(mascota.getUsuario() != null ? mascota.getUsuario().getNombreCompleto() : null)
                .usuarioId(mascota.getUsuario() != null ? mascota.getUsuario().getId() : null)
                .build();
    }
}