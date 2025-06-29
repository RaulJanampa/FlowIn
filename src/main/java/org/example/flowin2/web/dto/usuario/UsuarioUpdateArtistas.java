package org.example.flowin2.web.dto.usuario;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class UsuarioUpdateArtistas {
    @NotEmpty(message = "La lista de gustos musicales no puede estar vacía")
    private List<String> artistasFavoritos;
}
