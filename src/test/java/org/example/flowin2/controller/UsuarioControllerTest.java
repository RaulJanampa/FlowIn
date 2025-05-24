package org.example.flowin2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.controller.UsuarioController;
import org.example.flowin2.web.dto.usuario.UsuarioRequest;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    public UsuarioControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testSignUpUsuario() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setUsername("testuser");
        usuarioRequest.setPassword("password123");

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(1L);
        usuarioResponse.setUsername("testuser");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");

        when(usuarioService.save(any(UsuarioRequest.class))).thenReturn(usuarioResponse);
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/usuario/registrarse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bearer mocked-jwt-token"));

        verify(usuarioService).save(any(UsuarioRequest.class));
        verify(usuarioService).findByUsername("testuser");
        verify(jwtService).generateToken(usuario);
    }

    @Test
    public void testVerPerfil() throws Exception {
        String token = "mocked-jwt-token";
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(1L);
        usuarioResponse.setUsername("testuser");

        when(usuarioService.obtenerPerfil(token)).thenReturn(usuarioResponse);

        mockMvc.perform(get("/usuario/perfil")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(usuarioResponse)));

        verify(usuarioService).obtenerPerfil(token);
    }
}