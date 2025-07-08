package org.example.flowin2.domain.chatMessage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowin2.domain.sala.model.Sala;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // ID de mensaje

    private String username;
    private String contenido;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sala_id") // Nombre de la columna en la tabla chat_message
    @JsonBackReference
    private Sala sala;
}
