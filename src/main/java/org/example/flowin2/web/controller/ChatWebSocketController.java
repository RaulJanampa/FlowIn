package org.example.flowin2.web.controller;

import jakarta.transaction.Transactional;
import org.example.flowin2.application.chat.ChatService;
import org.example.flowin2.domain.chatMessage.model.ChatMessage;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.dto.chatMessage.ChatMessageDTO;
import org.example.flowin2.web.dto.musicControlMessage.MusicControlMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final JwtService jwtService;
    private final SalaRepository salaRepository;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
                                   ChatService chatService, JwtService jwtService,
                                   SalaRepository salaRepository) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.jwtService = jwtService;
        this.salaRepository = salaRepository;
    }

    @Transactional  // Asegurarse de que la transacción esté abierta
    @MessageMapping("/chat.send")
    public void enviarMensaje(ChatMessageDTO message, @Header("Authorization") String token) {
        try {
            String username = jwtService.extractUserName(token.replace("Bearer ", ""));

            // Recuperamos la sala y inicializamos explícitamente las colecciones
            Optional<Sala> optionalSala = salaRepository.findById(message.getSalaId());
            if (optionalSala.isEmpty()) {
                throw new RuntimeException("Sala no encontrada");
            }
            Sala sala = optionalSala.get();

            // Inicializamos la colección 'usuariosConectados' para evitar LazyLoading
            sala.getUsuariosConectados().size();  // Esto inicializa la colección 'usuariosConectados'

            // Ahora guardamos el mensaje
            List<ChatMessage> mensajesActualizados = chatService.guardarMensaje(
                    message.getSalaId(), username, message.getContenido()
            );

            // Enviar el mensaje actualizado al frontend
            messagingTemplate.convertAndSend("/topic/sala/" + message.getSalaId(), mensajesActualizados);
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/error/" + message.getSalaId(),
                    "Error al procesar mensaje: " + e.getMessage()
            );
        }
    }

    @MessageMapping("/musica.control")
    public void controlarMusica(MusicControlMessage message, @Header("Authorization") String token) {
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));

        Optional<Sala> optionalSala = salaRepository.findById(message.getSalaId());
        if (optionalSala.isEmpty()) return;

        Sala sala = optionalSala.get();

        if (!sala.getHost().getUsername().equals(username)) {
            throw new RuntimeException("Solo el host puede controlar la música");
        }

        switch (message.getTipoEvento()) {
            case PLAY:
                sala.setReproduciendo(true);
                sala.setCancionActual(message.getArchivo());
                sala.setTimestampInicio(System.currentTimeMillis());
                break;
            case PAUSE:
                sala.setReproduciendo(false);
                break;
            case RESUME:
                sala.setReproduciendo(true);
                long offset = message.getOffset();
                sala.setTimestampInicio(System.currentTimeMillis() - offset);
                break;
        }

        salaRepository.save(sala);
        messagingTemplate.convertAndSend("/topic/sala/music/" + message.getSalaId(), sala);
    }
}
