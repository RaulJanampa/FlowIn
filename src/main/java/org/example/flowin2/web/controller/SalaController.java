package org.example.flowin2.web.controller;

import org.example.flowin2.application.sala.SalaService;
import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.sala.model.Estado;
import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.dto.sala.SalaRequest;
import org.example.flowin2.web.dto.sala.SalaResponse;
import org.example.flowin2.web.dto.sala.SalaUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private UsuarioService usuarioService; // Servicio para obtener el usuario

    @PostMapping()
    public ResponseEntity<SalaResponse> sala(@RequestBody @Validated SalaRequest salaRequest) {
        // Obtener el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtén el nombre de usuario (o usa otro campo si es necesario)

        // Busca el usuario por su nombre (o por el ID o el email, dependiendo de cómo esté configurado tu sistema)
        Usuario usuario = usuarioService.findByUsername(username);

        // Ahora pasamos al servicio para guardar la sala y asociarla al usuario
        SalaResponse salaResponse = salaService.save(salaRequest, usuario);

        return ResponseEntity.ok(salaResponse);
    }
    @PostMapping("/salir")
    public ResponseEntity<String> salirDeSala() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario.getTipo() == Tipo.HOST && usuario.getSalaComoHost() != null) {
            Sala sala = usuario.getSalaComoHost();

            // Elimina al usuario como host de la sala
            sala.setHost(null);
            sala.setIdHost(null);
            sala.setEstado(Estado.INACTIVA); // o cerrala según lógica

            // Revertir el rol del usuario
            usuario.setTipo(Tipo.USUARIO);
            usuario.setSalaComoHost(null);

            salaRepository.save(sala);
            // usuarioRepository.save(usuario); // opcional

            return ResponseEntity.ok("Saliste de la sala y perdiste el rol de HOST");
        }

        return ResponseEntity.badRequest().body("No eres host de ninguna sala");
    }



    @GetMapping("/buscar")
    public List<SalaResponse> buscarSalas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String artista
    ) {
        return salaService.buscarSalas(nombre, genero, artista);
    }

    @GetMapping("/{id}/{nombre}")
    public SalaResponse accederSala(
            @PathVariable Long id,
            @PathVariable String nombre,
            @RequestHeader("Authorization") String token
    ) {
        return salaService.unirUsuarioASala(token, id, nombre);
    }
    @GetMapping("/{id}")
    public SalaResponse accederSala(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        return salaService.unirUsuarioASala(token, id);
    }

    @PatchMapping("/{id}")
    public SalaResponse actualizarSala(
            @PathVariable Long id,
            @RequestBody SalaUpdateRequest request,
            @RequestHeader("Authorization") String token
    ) {
        return salaService.actualizarSalaComoHost(id, request, token);
    }
}
