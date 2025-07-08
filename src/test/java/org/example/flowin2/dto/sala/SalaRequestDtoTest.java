package org.example.flowin2.dto.sala;

import org.example.flowin2.domain.sala.model.Estado;
import org.example.flowin2.web.dto.sala.SalaRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalaRequestDtoTest {

    @Test
    public void testConstructorPorDefecto() {
        SalaRequest salaRequest = new SalaRequest();
        assertNull(salaRequest.getNombre());
        assertNull(salaRequest.getGenero());
        assertNull(salaRequest.getArtista());
        assertEquals(Estado.ACTIVA, salaRequest.getEstado());
        assertNull(salaRequest.getCanciones());
    }

    @Test
    public void testSettersYGetters() {
        SalaRequest salaRequest = new SalaRequest();

        String nombre = "Sala de Rock";
        List<String> genero = Arrays.asList("Rock", "Metal");
        String artista = "Metallica";
        Estado estado = Estado.INACTIVA;
        String canciones = "Enter Sandman, Nothing Else Matters";

        salaRequest.setNombre(nombre);
        salaRequest.setGenero(genero);
        salaRequest.setArtista(artista);
        salaRequest.setEstado(estado);
        salaRequest.setCanciones(canciones);

        assertEquals(nombre, salaRequest.getNombre());
        assertEquals(genero, salaRequest.getGenero());
        assertEquals(artista, salaRequest.getArtista());
        assertEquals(estado, salaRequest.getEstado());
        assertEquals(canciones, salaRequest.getCanciones());
    }

    @Test
    public void testEstadoPorDefecto() {
        SalaRequest salaRequest = new SalaRequest();
        assertEquals(Estado.ACTIVA, salaRequest.getEstado());
    }

    @Test
    public void testEqualsYHashCode() {
        List<String> genero1 = Arrays.asList("Rock");
        String canciones1 = "Cancion1";

        SalaRequest request1 = new SalaRequest();
        request1.setNombre("Sala 1");
        request1.setGenero(genero1);
        request1.setArtista("Artista 1");
        request1.setCanciones(canciones1);

        SalaRequest request2 = new SalaRequest();
        request2.setNombre("Sala 1");
        request2.setGenero(genero1);
        request2.setArtista("Artista 1");
        request2.setCanciones(canciones1);

        SalaRequest request3 = new SalaRequest();
        request3.setNombre("Sala 2");
        request3.setGenero(genero1);
        request3.setArtista("Artista 1");
        request3.setCanciones(canciones1);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToString() {
        SalaRequest request = new SalaRequest();
        request.setNombre("Sala Test");
        request.setGenero(Arrays.asList("Pop"));
        request.setArtista("Artista Test");
        request.setCanciones("Cancion Test");

        String toStringResult = request.toString();

        assertTrue(toStringResult.contains("nombre=Sala Test"));
        assertTrue(toStringResult.contains("genero=[Pop]"));
        assertTrue(toStringResult.contains("artista=Artista Test"));
        assertTrue(toStringResult.contains("estado=ACTIVA"));
        assertTrue(toStringResult.contains("canciones=Cancion Test"));
    }
}
