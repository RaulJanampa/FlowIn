package org.example.flowin2.web.controller;

import org.example.flowin2.application.usuario.UsuarioDetailsServiceImpl;
import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.dto.usuario.UsuarioRequest;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioDetailsServiceImpl userDetailsService;

    @PostMapping("/registrarse")
    public ResponseEntity<?> signUpUsuario(@RequestBody @Validated UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.save(usuarioRequest);

        Usuario usuario = usuarioService.findByUsername(usuarioRequest.getUsername());
        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok("Bearer " + token);
    }
    @GetMapping("/perfil")
    public UsuarioResponse verPerfil(@RequestHeader("Authorization") String token) {
        return usuarioService.obtenerPerfil(token);
    }
}
