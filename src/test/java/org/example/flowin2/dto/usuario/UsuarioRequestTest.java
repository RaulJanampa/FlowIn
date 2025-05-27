package org.example.flowin2.dto.usuario;

import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.web.dto.usuario.UsuarioRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioRequestTest {

    @Test
    public void testConstructorPorDefecto() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        assertNull(usuarioRequest.getUsername());
        assertNull(usuarioRequest.getMail());
        assertNull(usuarioRequest.getPassword());
        assertNull(usuarioRequest.getGustosMusicales());
        assertEquals(Tipo.USUARIO, usuarioRequest.getTipo());
        assertNull(usuarioRequest.getArtistasFavoritos());
    }

    @Test
    public void testSettersYGetters() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        String username = "usuario123";
        String mail = "usuario@example.com";
        String password = "contraseña123";
        List<String> gustosMusicales = Arrays.asList("Rock", "Pop");
        Tipo tipo = Tipo.ADMIN;
        List<String> artistasFavoritos = Arrays.asList("Artista1", "Artista2");

        usuarioRequest.setUsername(username);
        usuarioRequest.setMail(mail);
        usuarioRequest.setPassword(password);
        usuarioRequest.setGustosMusicales(gustosMusicales);
        usuarioRequest.setTipo(tipo);
        usuarioRequest.setArtistasFavoritos(artistasFavoritos);

        assertEquals(username, usuarioRequest.getUsername());
        assertEquals(mail, usuarioRequest.getMail());
        assertEquals(password, usuarioRequest.getPassword());
        assertEquals(gustosMusicales, usuarioRequest.getGustosMusicales());
        assertEquals(tipo, usuarioRequest.getTipo());
        assertEquals(artistasFavoritos, usuarioRequest.getArtistasFavoritos());
    }

    @Test
    public void testEqualsYHashCode() {
        List<String> gustos = Arrays.asList("Rock");
        List<String> artistas = Arrays.asList("Artista1");

        UsuarioRequest request1 = new UsuarioRequest();
        request1.setUsername("usuario1");
        request1.setMail("usuario1@example.com");
        request1.setPassword("pass1");
        request1.setGustosMusicales(gustos);
        request1.setArtistasFavoritos(artistas);

        UsuarioRequest request2 = new UsuarioRequest();
        request2.setUsername("usuario1");
        request2.setMail("usuario1@example.com");
        request2.setPassword("pass1");
        request2.setGustosMusicales(gustos);
        request2.setArtistasFavoritos(artistas);

        UsuarioRequest request3 = new UsuarioRequest();
        request3.setUsername("usuario2");
        request3.setMail("usuario1@example.com");
        request3.setPassword("pass1");
        request3.setGustosMusicales(gustos);
        request3.setArtistasFavoritos(artistas);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToString() {
        UsuarioRequest request = new UsuarioRequest();
        request.setUsername("testuser");
        request.setMail("test@example.com");
        request.setPassword("testpass");
        request.setGustosMusicales(Arrays.asList("Rock"));
        request.setArtistasFavoritos(Arrays.asList("Artista1"));

        String toStringResult = request.toString();

        assertTrue(toStringResult.contains("username=testuser"));
        assertTrue(toStringResult.contains("mail=test@example.com"));
        assertTrue(toStringResult.contains("gustosMusicales=[Rock]"));
        assertTrue(toStringResult.contains("tipo=USUARIO"));
        assertTrue(toStringResult.contains("artistasFavoritos=[Artista1]"));
    }
}