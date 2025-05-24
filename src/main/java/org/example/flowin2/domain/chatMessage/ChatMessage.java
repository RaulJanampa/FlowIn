package org.example.flowin2.domain.chatMessage;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String username;
    private String contenido;
    private LocalDateTime timestamp;
}
