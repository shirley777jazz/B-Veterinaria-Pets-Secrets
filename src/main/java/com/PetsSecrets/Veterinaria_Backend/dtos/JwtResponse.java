package com.PetsSecrets.Veterinaria_Backend.dtos;

import lombok.Data;
import com.PetsSecrets.Veterinaria_Backend.models.Usuario;

@Data
public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String email;
    private String nombreCompleto;
    private String telefono;
    private String rol;

    public JwtResponse() {
    }

    public JwtResponse(String token, String type, Integer id, String email, String nombreCompleto, String telefono, String rol) {
        this.token = token;
        this.type = type != null ? type : "Bearer";
        this.id = id;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.rol = rol;
    }
    
    public JwtResponse(String accessToken, Usuario usuario) {
        this.token = accessToken;
        this.type = "Bearer";
        if (usuario != null) {
            this.id = usuario.getId();
            this.email = usuario.getEmail();
            this.nombreCompleto = usuario.getNombreCompleto();
            this.telefono = usuario.getTelefono();
            this.rol = usuario.getRol() != null ? usuario.getRol().name() : null;
        }
    }
}