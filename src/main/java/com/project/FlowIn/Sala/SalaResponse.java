package com.project.FlowIn.Sala;

import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Domain.UsuarioResponse;
import lombok.Data;

import java.util.List;
@Data
public class SalaResponse {
    private Long id;
    private String nombre;
    private List<String> genero;
    private String artista;
    private List<String> canciones;
    private List<UsuarioResponse> usuariosConectados;
}
