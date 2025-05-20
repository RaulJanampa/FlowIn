package com.project.FlowIn.Usuario.Domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String password;
    //Puede que no quiera dar sus gustos
    private List<String> gustosMusicales;
    //hay que ver como hacemos para el login de un usuario y traerlo de Security
    private Tipo tipo;
    //Puede ser nulo
    private List<String> artistasFavoritos;
}