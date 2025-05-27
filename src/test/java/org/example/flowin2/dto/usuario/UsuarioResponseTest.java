package org.example.flowin2.dto.usuario;

import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioResponseTest {

    @Test
    public void testConstructorPorDefecto() {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        assertNull(usuarioResponse.getId());
        assertNull(usuarioResponse.getUsername());
        assertNull(usuarioResponse.getMail());
        assertNull(usuarioResponse.getGustosMusicales());
        assertNull(usuarioResponse.getTipo());
        assertNull(usuarioResponse.getArtistasFavoritos());
    }

    @Test
    public void testSettersYGetters() {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        Long id = 1L;
        String username = "usuario123";
        String mail = "usuario@example.com";
        List<String> gustosMusicales = Arrays.asList("Rock", "Pop");
        Tipo tipo = Tipo.ADMIN;
        List<String> artistasFavoritos = Arrays.asList("Artista1", "Artista2");

        usuarioResponse.setId(id);
        usuarioResponse.setUsername(username);
        usuarioResponse.setMail(mail);
        usuarioResponse.setGustosMusicales(gustosMusicales);
        usuarioResponse.setTipo(tipo);
        usuarioResponse.setArtistasFavoritos(artistasFavoritos);

        assertEquals(id, usuarioResponse.getId());
        assertEquals(username, usuarioResponse.getUsername());
        assertEquals(mail, usuarioResponse.getMail());
        assertEquals(gustosMusicales, usuarioResponse.getGustosMusicales());
        assertEquals(tipo, usuarioResponse.getTipo());
        assertEquals(artistasFavoritos, usuarioResponse.getArtistasFavoritos());
    }

    @Test
    public void testEqualsYHashCode() {
        List<String> gustos = Arrays.asList("Rock");
        List<String> artistas = Arrays.asList("Artista1");

        UsuarioResponse response1 = new UsuarioResponse();
        response1.setId(1L);
        response1.setUsername("usuario1");
        response1.setMail("usuario1@example.com");
        response1.setGustosMusicales(gustos);
        response1.setTipo(Tipo.USUARIO);
        response1.setArtistasFavoritos(artistas);

        UsuarioResponse response2 = new UsuarioResponse();
        response2.setId(1L);
        response2.setUsername("usuario1");
        response2.setMail("usuario1@example.com");
        response2.setGustosMusicales(gustos);
        response2.setTipo(Tipo.USUARIO);
        response2.setArtistasFavoritos(artistas);

        UsuarioResponse response3 = new UsuarioResponse();
        response3.setId(2L);
        response3.setUsername("usuario1");
        response3.setMail("usuario1@example.com");
        response3.setGustosMusicales(gustos);
        response3.setTipo(Tipo.USUARIO);
        response3.setArtistasFavoritos(artistas);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToString() {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setUsername("testuser");
        response.setMail("test@example.com");
        response.setGustosMusicales(Arrays.asList("Rock"));
        response.setTipo(Tipo.USUARIO);
        response.setArtistasFavoritos(Arrays.asList("Artista1"));

        String toStringResult = response.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("username=testuser"));
        assertTrue(toStringResult.contains("mail=test@example.com"));
        assertTrue(toStringResult.contains("gustosMusicales=[Rock]"));
        assertTrue(toStringResult.contains("tipo=USUARIO"));
        assertTrue(toStringResult.contains("artistasFavoritos=[Artista1]"));
    }
}
