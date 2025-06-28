package org.example.flowin2.application.chat;

import org.example.flowin2.domain.chatMessage.ChatMessage;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ChatService {

    private final SalaRepository salaRepository;

    public ChatService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<ChatMessage> guardarMensaje(Long salaId, String username, String contenido) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        ChatMessage nuevo = new ChatMessage(username, contenido, LocalDateTime.now());

        List<ChatMessage> mensajes = sala.getMensajesChat();
        if (mensajes.size() >= 100) {
            mensajes.remove(0); // elimina el más antiguo
        }

        mensajes.add(nuevo);
        salaRepository.save(sala);

        return mensajes;
    }
}
