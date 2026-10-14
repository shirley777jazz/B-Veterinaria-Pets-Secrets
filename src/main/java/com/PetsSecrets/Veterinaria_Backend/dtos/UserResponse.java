package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Usuario;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String rol;
    private LocalDateTime fechaRegistro;
    
    public UserResponse() {
    }

    public UserResponse(Integer id, String nombreCompleto, String email, String telefono, String rol, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    public UserResponse(Usuario usuario) {
        if (usuario != null) {
            this.id = usuario.getId();
            this.nombreCompleto = usuario.getNombreCompleto();
            this.email = usuario.getEmail();
            this.telefono = usuario.getTelefono();
            this.rol = usuario.getRol() != null ? usuario.getRol().name() : null;
            this.fechaRegistro = usuario.getFechaRegistro();
        }
    }
}