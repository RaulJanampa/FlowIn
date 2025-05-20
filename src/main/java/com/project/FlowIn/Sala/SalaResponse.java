package com.project.FlowIn.Sala;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class SalaResponse {

    private String nombre;
    private List<String> género;
    private String artista;
    private String epoca;
    private List<String> canciones;
}
