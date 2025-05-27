package org.example.flowin2.dto;

import org.example.flowin2.web.dto.sala.SalaUpdateRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalaUpdateRequestDtoTest {

    @Test
    public void testConstructorPorDefecto() {
        SalaUpdateRequest dto = new SalaUpdateRequest();
        assertNull(dto.getNombre());
        assertNull(dto.getGenero());
        assertNull(dto.getArtista());
        assertNull(dto.getCanciones());
    }

    @Test
    public void testSettersYGetters() {
        SalaUpdateRequest dto = new SalaUpdateRequest();

        String nombre = "Sala Actualizada";
        String genero = "Pop, Rock";
        String artista = "Diversos Artistas";
        List<String> canciones = Arrays.asList("Canción 1", "Canción 2");

        dto.setNombre(nombre);
        dto.setGenero(genero);
        dto.setArtista(artista);
        dto.setCanciones(canciones);

        assertEquals(nombre, dto.getNombre());
        assertEquals(genero, dto.getGenero());
        assertEquals(artista, dto.getArtista());
        assertEquals(canciones, dto.getCanciones());
    }

    @Test
    public void testEqualsYHashCode() {
        List<String> canciones = Arrays.asList("Cancion1");

        SalaUpdateRequest request1 = new SalaUpdateRequest();
        request1.setNombre("Sala 1");
        request1.setGenero("Rock");
        request1.setArtista("Artista 1");
        request1.setCanciones(canciones);

        SalaUpdateRequest request2 = new SalaUpdateRequest();
        request2.setNombre("Sala 1");
        request2.setGenero("Rock");
        request2.setArtista("Artista 1");
        request2.setCanciones(canciones);

        SalaUpdateRequest request3 = new SalaUpdateRequest();
        request3.setNombre("Sala 2");
        request3.setGenero("Rock");
        request3.setArtista("Artista 1");
        request3.setCanciones(canciones);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToString() {
        SalaUpdateRequest request = new SalaUpdateRequest();
        request.setNombre("Sala Test");
        request.setGenero("Pop");
        request.setArtista("Artista Test");
        request.setCanciones(Arrays.asList("Cancion Test"));

        String toStringResult = request.toString();

        assertTrue(toStringResult.contains("nombre=Sala Test"));
        assertTrue(toStringResult.contains("genero=Pop"));
        assertTrue(toStringResult.contains("artista=Artista Test"));
        assertTrue(toStringResult.contains("canciones=[Cancion Test]"));
    }
}
