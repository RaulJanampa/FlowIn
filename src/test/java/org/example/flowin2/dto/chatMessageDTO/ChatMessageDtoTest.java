package org.example.flowin2.dto;

import org.example.flowin2.web.dto.chatMessage.ChatMessageDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageDtoTest {

    @Test
    public void testConstructorVacio() {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        assertNull(chatMessageDTO.getSalaId());
        assertNull(chatMessageDTO.getContenido());
    }

    @Test
    public void testConstructorConArgumentos() {
        Long salaId = 1L;
        String contenido = "Hola mundo";

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO(salaId, contenido);

        assertEquals(salaId, chatMessageDTO.getSalaId());
        assertEquals(contenido, chatMessageDTO.getContenido());
    }

    @Test
    public void testSettersYGetters() {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

        Long salaId = 2L;
        String contenido = "Mensaje de prueba";

        chatMessageDTO.setSalaId(salaId);
        chatMessageDTO.setContenido(contenido);

        assertEquals(salaId, chatMessageDTO.getSalaId());
        assertEquals(contenido, chatMessageDTO.getContenido());
    }

    @Test
    public void testEqualsYHashCode() {
        ChatMessageDTO mensaje1 = new ChatMessageDTO(1L, "Hola");
        ChatMessageDTO mensaje2 = new ChatMessageDTO(1L, "Hola");
        ChatMessageDTO mensaje3 = new ChatMessageDTO(2L, "Adios");

        assertEquals(mensaje1, mensaje2);
        assertEquals(mensaje1.hashCode(), mensaje2.hashCode());
        assertNotEquals(mensaje1, mensaje3);
    }

    @Test
    public void testToString() {
        ChatMessageDTO mensaje = new ChatMessageDTO(1L, "Contenido del mensaje");
        String toStringResultado = mensaje.toString();

        assertTrue(toStringResultado.contains("salaId=1"));
        assertTrue(toStringResultado.contains("contenido=Contenido del mensaje"));
    }
}
