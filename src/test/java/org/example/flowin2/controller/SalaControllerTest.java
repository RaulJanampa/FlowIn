package org.example.flowin2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flowin2.application.sala.SalaService;
import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.web.controller.SalaController;
import org.example.flowin2.web.dto.sala.SalaRequest;
import org.example.flowin2.web.dto.sala.SalaResponse;
import org.example.flowin2.web.dto.sala.SalaUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SalaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SalaService salaService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaController salaController;


    public SalaControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(salaController).build();
    }

    @Test
    public void testSala() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        SalaRequest salaRequest = new SalaRequest();
        salaRequest.setNombre("Sala Test");

        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");

        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Test");

        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(salaService.save(any(SalaRequest.class), eq(usuario))).thenReturn(salaResponse);

        mockMvc.perform(post("/sala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(salaRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(usuarioService).findByUsername("testuser");
        verify(salaService).save(any(SalaRequest.class), eq(usuario));

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testSalirDeSalaComoHost() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        Usuario usuario = new Usuario();
        usuario.setTipo(Tipo.HOST);
        Sala sala = new Sala();
        sala.setId(1L);
        usuario.setSalaComoHost(sala);

        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/sala/salir"))
                .andExpect(status().isOk())
                .andExpect(content().string("Saliste de la sala y perdiste el rol de HOST"));

        verify(usuarioService).findByUsername("testuser");
        verify(salaRepository).save(sala);

        assertEquals(Tipo.USUARIO, usuario.getTipo());
        assertNull(usuario.getSalaComoHost());

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testSalaComoHost() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("hostuser");
        SecurityContextHolder.setContext(securityContext);

        SalaRequest salaRequest = new SalaRequest();
        salaRequest.setNombre("Sala Host");

        Usuario usuario = new Usuario();
        usuario.setUsername("hostuser");
        usuario.setTipo(Tipo.HOST);

        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Host");

        when(usuarioService.findByUsername("hostuser")).thenReturn(Optional.of(usuario));
        when(salaService.save(any(SalaRequest.class), eq(usuario))).thenReturn(salaResponse);

        mockMvc.perform(post("/sala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(salaRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(usuarioService).findByUsername("hostuser");
        verify(salaService).save(any(SalaRequest.class), eq(usuario));

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testSalirDeSala() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        Usuario usuario = new Usuario();
        usuario.setTipo(Tipo.HOST);
        Sala sala = new Sala();
        sala.setId(1L);
        usuario.setSalaComoHost(sala);

        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/sala/salir"))
                .andExpect(status().isOk())
                .andExpect(content().string("Saliste de la sala y perdiste el rol de HOST"));

        verify(usuarioService).findByUsername("testuser");
        verify(salaRepository).save(sala);

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testBuscarSalas() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Test");

        when(salaService.buscarSalas("Sala Test", null, null)).thenReturn(List.of(salaResponse));

        mockMvc.perform(get("/sala/buscar")
                        .param("nombre", "Sala Test"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(salaResponse))));

        verify(salaService).buscarSalas("Sala Test", null, null);
    }

    @Test
    public void testBuscarSalaPorId() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Test");

        when(salaService.unirUsuarioASala("mocked-token", 1L)).thenReturn(salaResponse);

        mockMvc.perform(get("/sala/1")
                        .header("Authorization", "mocked-token"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(salaService).unirUsuarioASala("mocked-token", 1L);
    }

    @Test
    public void testAccederSalaConNombre() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Test");

        when(salaService.unirUsuarioASala("mocked-token", 1L, "Sala Test")).thenReturn(salaResponse);

        mockMvc.perform(get("/sala/1/Sala Test")
                        .header("Authorization", "mocked-token"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(salaService).unirUsuarioASala("mocked-token", 1L, "Sala Test");
    }

    @Test
    public void testAccederSalaSinNombre() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Test");

        when(salaService.unirUsuarioASala("mocked-token", 1L)).thenReturn(salaResponse);

        mockMvc.perform(get("/sala/1")
                        .header("Authorization", "mocked-token"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(salaService).unirUsuarioASala("mocked-token", 1L);
    }

    @Test
    public void testActualizarSala() throws Exception {
        SalaUpdateRequest salaUpdateRequest = new SalaUpdateRequest();
        salaUpdateRequest.setNombre("Sala Actualizada");

        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Actualizada");

        when(salaService.actualizarSalaComoHost(eq(1L), any(SalaUpdateRequest.class), eq("mocked-token"))).thenReturn(salaResponse);

        mockMvc.perform(patch("/sala/1")
                        .header("Authorization", "mocked-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(salaUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(salaResponse)));

        verify(salaService).actualizarSalaComoHost(eq(1L), any(SalaUpdateRequest.class), eq("mocked-token"));
    }

    @Test
    public void testFiltrarSalasPorNombre() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Filtrada");

        when(salaService.buscarSalas("Sala Filtrada", null, null)).thenReturn(List.of(salaResponse));

        mockMvc.perform(get("/sala/buscar")
                        .param("nombre", "Sala Filtrada"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(salaResponse))));

        verify(salaService).buscarSalas("Sala Filtrada", null, null);
    }

    @Test
    public void testFiltrarSalasPorCanciones() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Canciones");

        when(salaService.buscarSalas(null, null, "Cancion Test")).thenReturn(List.of(salaResponse));

        mockMvc.perform(get("/sala/buscar")
                        .param("artista", "Cancion Test"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(salaResponse))));

        verify(salaService).buscarSalas(null, null, "Cancion Test");
    }

    @Test
    public void testFiltrarSalasPorGenero() throws Exception {
        SalaResponse salaResponse = new SalaResponse();
        salaResponse.setId(1L);
        salaResponse.setNombre("Sala Genero");

        when(salaService.buscarSalas(null, "Genero Test", null)).thenReturn(List.of(salaResponse));

        mockMvc.perform(get("/sala/buscar")
                        .param("genero", "Genero Test"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(salaResponse))));

        verify(salaService).buscarSalas(null, "Genero Test", null);
    }
}
