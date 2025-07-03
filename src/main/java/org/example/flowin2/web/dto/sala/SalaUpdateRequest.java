package org.example.flowin2.web.dto.sala;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class SalaUpdateRequest {
    private String nombre;
    private List<@NotEmpty String> genero;
    private String artista;
    private String canciones;
}
