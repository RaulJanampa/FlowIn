package org.example.flowin2.web.controller;

import org.example.flowin2.application.chat.ChatService;
import org.example.flowin2.domain.chatMessage.ChatMessage;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.dto.chatMessage.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private JwtService jwtService;

    @MessageMapping("/chat.send") // /app/chat.send
    public void enviarMensaje(ChatMessageDTO message, @Header("Authorization") String token) {
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));

        List<ChatMessage> mensajesActualizados = chatService.guardarMensaje(
                message.getSalaId(), username, message.getContenido()
        );

        messagingTemplate.convertAndSend("/topic/sala/" + message.getSalaId(), mensajesActualizados);
    }
}
