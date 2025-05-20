package com.project.FlowIn.Sala;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
public class SalaRequest {

    @NotBlank(message = "El nombre de la sala no puede estar vacío")
    private String nombre;

    @NotNull(message = "El género no puede ser nulo")
    @Size(min = 1, message = "Debe haber al menos un género")
    private List<String> género;

    @NotBlank(message = "El artista no puede estar vacío")
    private String artista;

    //puede que se decida a esuchar discografia variada
    private String epoca;

    //Por defecto el estado va a estar activo porq se estará creando una sala
    private Estado estado = Estado.ACTIVA;

    @NotNull(message = "La lista de canciones no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos una canción")
    private List<String> canciones;
}
