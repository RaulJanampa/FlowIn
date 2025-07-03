package org.example.flowin2.web.dto.sala;

import lombok.Data;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;

import java.util.List;
@Data
public class SalaResponse {
    private Long id;
    private String nombre;
    private List<String> genero;
    private String artista;
    private String canciones;
    private List<UsuarioResponse> usuariosConectados;
}
