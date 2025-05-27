package org.example.flowin2.web.controller;

import org.example.flowin2.application.sala.SalaService;
import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.sala.model.Estado;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.shared.exceptions.ResourceConflictException;
import org.example.flowin2.shared.exceptions.ResourceNotFoundException;
import org.example.flowin2.web.dto.sala.SalaRequest;
import org.example.flowin2.web.dto.sala.SalaResponse;
import org.example.flowin2.web.dto.sala.SalaUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;
    private final JwtService jwtService;
    private final SalaRepository salaRepository;
    private final UsuarioService usuarioService;

    public SalaController(SalaService salaService, JwtService jwtService, SalaRepository salaRepository, UsuarioService usuarioService) {
        this.salaService = salaService;
        this.jwtService = jwtService;
        this.salaRepository = salaRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<SalaResponse> sala(@RequestBody @Validated SalaRequest salaRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        SalaResponse salaResponse = salaService.save(salaRequest, usuario);

        return ResponseEntity.status(201).body(salaResponse);
    }

    @PostMapping("/salir")
    public ResponseEntity<String> salirDeSala() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        if (usuario.getTipo() == Tipo.HOST && usuario.getSalaComoHost() != null) {
            Sala sala = usuario.getSalaComoHost();

            sala.setHost(null);
            sala.setEstado(Estado.INACTIVA);

            usuario.setTipo(Tipo.USUARIO);
            usuario.setSalaComoHost(null);

            salaRepository.save(sala);

            return ResponseEntity.ok("Saliste de la sala y perdiste el rol de HOST");
        }

        throw new ResourceConflictException("No eres host de ninguna sala");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<SalaResponse>> buscarSalas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String artista
    ) {
        List<SalaResponse> salas = salaService.buscarSalas(nombre, genero, artista);
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}/{nombre}")
    public ResponseEntity<SalaResponse> accederSala(
            @PathVariable Long id,
            @PathVariable String nombre,
            @RequestHeader("Authorization") String token
    ) {
        SalaResponse sala = salaService.unirUsuarioASala(token, id, nombre);
        return ResponseEntity.ok(sala);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> accederSala(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        SalaResponse sala = salaService.unirUsuarioASala(token, id);
        return ResponseEntity.ok(sala);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalaResponse> actualizarSala(
            @PathVariable Long id,
            @RequestBody SalaUpdateRequest request,
            @RequestHeader("Authorization") String token
    ) {
        SalaResponse salaActualizada = salaService.actualizarSalaComoHost(id, request, token);
        return ResponseEntity.ok(salaActualizada);
    }
}