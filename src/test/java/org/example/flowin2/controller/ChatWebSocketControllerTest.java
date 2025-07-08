package org.example.flowin2.controller;

import org.example.flowin2.application.chat.ChatService;
import org.example.flowin2.domain.chatMessage.model.ChatMessage;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.controller.ChatWebSocketController;
import org.example.flowin2.web.dto.chatMessage.ChatMessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatWebSocketControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private ChatService chatService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ChatWebSocketController chatWebSocketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarMensaje() {
        // Arrange
        String token = "Bearer testToken";
        String username = "testUser";
        String salaId = "123";
        String contenido = "Hola!";
        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setSalaId(Long.valueOf(salaId));
        messageDTO.setContenido(contenido);

        List<ChatMessage> mensajesActualizados = List.of(new ChatMessage());
        when(jwtService.extractUserName("testToken")).thenReturn(username);
        when(chatService.guardarMensaje(Long.valueOf(salaId), username, contenido)).thenReturn(mensajesActualizados);

        chatWebSocketController.enviarMensaje(messageDTO, token);

        verify(jwtService).extractUserName("testToken");
        verify(chatService).guardarMensaje(Long.valueOf(salaId), username, contenido);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List<ChatMessage>> messagesCaptor = ArgumentCaptor.forClass(List.class);
        verify(messagingTemplate).convertAndSend(destinationCaptor.capture(), messagesCaptor.capture());

        assertEquals("/topic/sala/" + salaId, destinationCaptor.getValue());
        assertEquals(mensajesActualizados, messagesCaptor.getValue());
    }
}
