package org.example.flowin2.dto.sala;

import org.example.flowin2.web.dto.sala.SalaResponse;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalaResponseDtoTest {

    @Test
    public void testConstructorPorDefecto() {
        SalaResponse salaResponse = new SalaResponse();
        assertNull(salaResponse.getId());
        assertNull(salaResponse.getNombre());
        assertNull(salaResponse.getGenero());
        assertNull(salaResponse.getArtista());
        assertNull(salaResponse.getCanciones());
        assertNull(salaResponse.getUsuariosConectados());
    }

    @Test
    public void testSettersYGetters() {
        SalaResponse salaResponse = new SalaResponse();

        Long id = 1L;
        String nombre = "Sala de Jazz";
        List<String> genero = Arrays.asList("Jazz", "Blues");
        String artista = "Miles Davis";
        String canciones = "So What, Blue in Green";
        List<UsuarioResponse> usuarios = Arrays.asList(new UsuarioResponse());

        salaResponse.setId(id);
        salaResponse.setNombre(nombre);
        salaResponse.setGenero(genero);
        salaResponse.setArtista(artista);
        salaResponse.setCanciones(canciones);
        salaResponse.setUsuariosConectados(usuarios);

        assertEquals(id, salaResponse.getId());
        assertEquals(nombre, salaResponse.getNombre());
        assertEquals(genero, salaResponse.getGenero());
        assertEquals(artista, salaResponse.getArtista());
        assertEquals(canciones, salaResponse.getCanciones());
        assertEquals(usuarios, salaResponse.getUsuariosConectados());
    }

    @Test
    public void testEqualsYHashCode() {
        List<String> genero = Arrays.asList("Rock");
        String canciones = "Cancion1";
        List<UsuarioResponse> usuarios = Arrays.asList(new UsuarioResponse());

        SalaResponse response1 = new SalaResponse();
        response1.setId(1L);
        response1.setNombre("Sala 1");
        response1.setGenero(genero);
        response1.setArtista("Artista 1");
        response1.setCanciones(canciones);
        response1.setUsuariosConectados(usuarios);

        SalaResponse response2 = new SalaResponse();
        response2.setId(1L);
        response2.setNombre("Sala 1");
        response2.setGenero(genero);
        response2.setArtista("Artista 1");
        response2.setCanciones(canciones);
        response2.setUsuariosConectados(usuarios);

        SalaResponse response3 = new SalaResponse();
        response3.setId(2L);
        response3.setNombre("Sala 1");
        response3.setGenero(genero);
        response3.setArtista("Artista 1");
        response3.setCanciones(canciones);
        response3.setUsuariosConectados(usuarios);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToString() {
        SalaResponse response = new SalaResponse();
        response.setId(1L);
        response.setNombre("Sala Test");
        response.setGenero(Arrays.asList("Pop"));
        response.setArtista("Artista Test");
        response.setCanciones("Cancion Test");
        response.setUsuariosConectados(Arrays.asList(new UsuarioResponse()));

        String toStringResult = response.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("nombre=Sala Test"));
        assertTrue(toStringResult.contains("genero=[Pop]"));
        assertTrue(toStringResult.contains("artista=Artista Test"));
        assertTrue(toStringResult.contains("canciones=Cancion Test"));
        assertTrue(toStringResult.contains("usuariosConectados="));
    }
}