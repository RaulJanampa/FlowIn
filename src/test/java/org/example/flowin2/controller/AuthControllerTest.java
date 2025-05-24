package org.example.flowin2.controller;

import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;


    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }


    @Test
    void testLogin() {
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedPassword";
        String token = "testToken";

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encodedPassword);

        when(usuarioService.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(usuario)).thenReturn(token);

        ResponseEntity<String> response = authController.login(username, password);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bearer " + token, response.getBody());

        verify(usuarioService).findByUsername(username);
        verify(passwordEncoder).matches(password, encodedPassword);
        verify(jwtService).generateToken(usuario);
    }

    @Test
    void testLoginInvalidCredentials() {
        String username = "testUser";
        String password = "testPassword";

        when(usuarioService.findByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.login(username, password);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciales inválidas", response.getBody());

        verify(usuarioService).findByUsername(username);
        verifyNoInteractions(passwordEncoder, jwtService);
    }
}
