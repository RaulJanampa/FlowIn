package org.example.flowin2.application.chat;

import org.example.flowin2.domain.chatMessage.model.ChatMessage;
import org.example.flowin2.domain.chatMessage.repository.ChatMessageRepository;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final SalaRepository salaRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(SalaRepository salaRepository, ChatMessageRepository chatMessageRepository) {
        this.salaRepository = salaRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public List<ChatMessage> guardarMensaje(Long salaId, String username, String contenido) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        ChatMessage nuevo = new ChatMessage();
        nuevo.setUsername(username);
        nuevo.setContenido(contenido);
        nuevo.setTimestamp(LocalDateTime.now());
        nuevo.setSala(sala); // Asociamos el mensaje con la sala

        // Guardamos el nuevo mensaje
        chatMessageRepository.save(nuevo);

        List<ChatMessage> mensajes = sala.getMensajesChat();
        if (mensajes.size() >= 100) {
            mensajes.remove(0); // elimina el más antiguo
        }

        mensajes.add(nuevo); // Añadimos el nuevo mensaje

        salaRepository.save(sala); // Guardamos la sala con los mensajes actualizados
        return mensajes;
    }
}
