package org.example.flowin2.web.dto.musicControlMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowin2.domain.music.tipoEvento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicControlMessage {
    private Long salaId;
    private tipoEvento tipoEvento;
    private String archivo;  // Solo en PLAY
    private Long offset;     // Solo en RESUME
}

