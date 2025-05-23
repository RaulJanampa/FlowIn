package org.example.flowin2.web.dto.usuario;

import lombok.Data;
import org.example.flowin2.domain.usuario.model.Tipo;

import java.util.List;

@Data
public class UsuarioResponse {
    private Long id;
    private String username;
    private String mail;
    private List<String> gustosMusicales;
    private Tipo tipo;
    private List<String> artistasFavoritos;

}