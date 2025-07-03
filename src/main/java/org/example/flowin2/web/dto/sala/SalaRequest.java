package org.example.flowin2.web.dto.sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.flowin2.domain.sala.model.Estado;

import java.util.List;

@Data
public class SalaRequest {
    @NotBlank(message = "El nombre de la sala no puede estar vacío")
    private String nombre;

    @NotNull(/*message = "El género no puede ser nulo"*/)
    @Size(min = 1, message = "Debe haber al menos un género")
    private List<@NotEmpty String> genero;

    @NotBlank(message = "El artista no puede estar vacío")
    private String artista;

    //Por defecto el estado va a estar activo porq se estará creando una sala
    private Estado estado = Estado.ACTIVA;

    @jakarta.validation.constraints.NotNull
    private String canciones;
}
