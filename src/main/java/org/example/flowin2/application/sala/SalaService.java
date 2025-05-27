package org.example.flowin2.application.sala;

import org.example.flowin2.domain.sala.model.Sala;
import org.example.flowin2.domain.sala.repository.SalaRepository;
import org.example.flowin2.domain.usuario.model.Tipo;
import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.domain.usuario.repository.UsuarioRepository;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.shared.exceptions.ResourceNotFoundException;
import org.example.flowin2.web.dto.sala.SalaRequest;
import org.example.flowin2.web.dto.sala.SalaResponse;
import org.example.flowin2.web.dto.sala.SalaUpdateRequest;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public SalaResponse save(SalaRequest salaRequest, Usuario usuario) {
        Sala sala = modelMapper.map(salaRequest, Sala.class);

        sala.setHost(usuario);

        usuario.setTipo(Tipo.HOST);
        usuario.setSalaComoHost(sala);

        sala.setUsuariosConectados(List.of(usuario));

        Sala salaGuardada = salaRepository.save(sala);
        return modelMapper.map(salaGuardada, SalaResponse.class);
    }

    public List<SalaResponse> buscarSalas(String nombre, String genero, String artista) {
        List<Sala> salas = salaRepository.findAll();

        return salas.stream()
                .filter(sala -> (nombre == null || sala.getNombre().equalsIgnoreCase(nombre)) &&
                        (genero == null || sala.getGenero().stream().anyMatch(genero::equals)))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SalaResponse unirUsuarioASala(String token, Long salaId, String salaNombre) {
        String username = jwtService.extractUserName(token.substring(7));
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + salaId));

        if (!sala.getNombre().equalsIgnoreCase(salaNombre)) {
            throw new IllegalArgumentException("Nombre de sala incorrecto");
        }

        if (!sala.getUsuariosConectados().contains(usuario)) {
            sala.getUsuariosConectados().add(usuario);
            salaRepository.save(sala);
        }

        return mapToResponse(sala);
    }

    public SalaResponse unirUsuarioASala(String token, Long salaId) {
        String username = jwtService.extractUserName(token.substring(7));
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + salaId));

        if (!sala.getUsuariosConectados().contains(usuario)) {
            sala.getUsuariosConectados().add(usuario);
            salaRepository.save(sala);
        }

        return mapToResponse(sala);
    }

    public SalaResponse actualizarSalaComoHost(Long id, SalaUpdateRequest request, String token) {
        String username = jwtService.extractUserName(token.substring(7));
        Usuario host = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + id));

        if (!sala.getHost().getId().equals(host.getId())) {
            throw new SecurityException("No autorizado. Solo el host puede editar esta sala.");
        }

        if (request.getNombre() != null) sala.setNombre(request.getNombre());
        if (request.getGenero() != null) sala.setGenero(Collections.singletonList(request.getGenero()));
        if (request.getCanciones() != null) sala.setCanciones(request.getCanciones());

        salaRepository.save(sala);
        return mapToResponse(sala);
    }

    private SalaResponse mapToResponse(Sala sala) {
        SalaResponse response = modelMapper.map(sala, SalaResponse.class);

        List<UsuarioResponse> usuariosConectados = sala.getUsuariosConectados().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponse.class))
                .collect(Collectors.toList());

        response.setUsuariosConectados(usuariosConectados);

        return response;
    }
}
