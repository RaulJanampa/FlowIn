package org.example.flowin2.web.dto.sala;

import lombok.Data;

import java.util.List;

@Data
public class SalaUpdateRequest {
    private String nombre;
    private String genero;
    private String artista;
    private List<String> canciones;
}
