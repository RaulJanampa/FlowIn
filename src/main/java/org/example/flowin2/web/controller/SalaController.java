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
import org.example.flowin2.web.dto.musicControlMessage.EstadoReproduccionDTO;
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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println("👤 Intentando salir usuario: " + username);

            salaService.salirDeSala(username);

            return ResponseEntity.ok("✅ Saliste de la sala correctamente");

        } catch (Exception e) {
            System.out.println("🔥 ERROR al salir de sala: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error interno al salir de la sala");
        }
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

    @GetMapping("/salas/{salaId}/musica/estado")
    public ResponseEntity<EstadoReproduccionDTO> getEstadoMusical(@PathVariable Long salaId) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        EstadoReproduccionDTO dto = new EstadoReproduccionDTO(
                sala.getCancionActual(),
                sala.getReproduciendo(),
                sala.getTimestampInicio()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/usuarios/conectados")
    public ResponseEntity<Integer> obtenerCantidadUsuariosConectados(@PathVariable Long id) {
        int cantidad = salaService.contarUsuariosConectados(id);
        return ResponseEntity.ok(cantidad);
    }
}