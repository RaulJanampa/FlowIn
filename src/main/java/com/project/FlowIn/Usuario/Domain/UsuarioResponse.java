package com.project.FlowIn.Usuario.Domain;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String mail;
    private List<String> gustosMusicales;
    private Tipo tipo;
    private List<String> artistasFavoritos;
}