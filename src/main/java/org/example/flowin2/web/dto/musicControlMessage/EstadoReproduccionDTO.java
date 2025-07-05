package org.example.flowin2.web.dto.musicControlMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoReproduccionDTO {
    private String cancionActual;
    private Boolean reproduciendo;
    private Long timestampInicio;
}
