package org.example.flowin2.application.sala;

import org.example.flowin2.application.usuario.UsuarioService;
import org.example.flowin2.domain.sala.model.Estado;
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

    @Autowired
    private UsuarioService usuarioService;

    public SalaResponse save(SalaRequest salaRequest, Usuario usuario) {
        Sala sala = modelMapper.map(salaRequest, Sala.class);

        sala.setHost(usuario);

        usuario.setTipo(Tipo.HOST);
        usuario.setSalaComoHost(sala);
        usuario.setSala(sala); // <- Asignar la sala al usuario también


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
            usuario.setSala(sala); // <-- Asignar la sala al usuario
            usuarioRepository.save(usuario); // <-- Guardar usuario con la sala asignada
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
            usuario.setSala(sala); // <-- Asignar la sala al usuario
            usuarioRepository.save(usuario); // <-- Guardar usuario con la sala asignada
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

        // ✅ Actualizar nombre si viene
        if (request.getNombre() != null && !request.getNombre().isBlank()) {
            sala.setNombre(request.getNombre());
        }

        // ✅ Actualizar género
        if (request.getGenero() != null) {
            sala.setGenero(request.getGenero());
        }

        // ✅ Actualizar artista
        if (request.getArtista() != null && !request.getArtista().isBlank()) {
            sala.setArtista(request.getArtista());
        }

        // ✅ Actualizar canciones (opcional, si es útil para ti)
        if (request.getCanciones() != null && !request.getCanciones().isBlank()) {
            sala.setCanciones(request.getCanciones());
        }

        // ✅ Actualizar canción actual + timestamp
        if (request.getCancionActual() != null && !request.getCancionActual().isBlank()) {
            sala.setCancionActual(request.getCancionActual());
            sala.setTimestampInicio(System.currentTimeMillis());
        }

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

    public void salirDeSala(String username) {
        System.out.println("➡️ Intentando salir de la sala: " + username);

        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        System.out.println("🔍 Tipo de usuario: " + usuario.getTipo());

        Sala sala;

        if (usuario.getTipo() == Tipo.HOST) {
            sala = usuario.getSalaComoHost();

            if (sala == null) {
                throw new IllegalStateException("El usuario es HOST pero no tiene sala asignada como host.");
            }

            System.out.println("🧹 El host eliminará la sala: " + sala.getNombre());

            // Limpia a todos los usuarios conectados
            for (Usuario u : sala.getUsuariosConectados()) {
                u.setSala(null);
                System.out.println("🚪 Usuario desconectado: " + u.getUsername());
            }

            // Limpia referencias del host
            usuario.setTipo(Tipo.USUARIO);
            usuario.setSalaComoHost(null);
            usuario.setSala(null);

            sala.getUsuariosConectados().clear(); // por seguridad

            // Borra la sala de la base de datos
            salaRepository.delete(sala);
            System.out.println("✅ Sala eliminada junto con todos los usuarios desconectados");

        } else {
            // Si no es HOST, busca la sala donde esté conectado
            sala = salaRepository.findAll().stream()
                    .filter(s -> s.getUsuariosConectados().contains(usuario))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("❌ No estás conectado a ninguna sala"));

            System.out.println("👤 Usuario será eliminado de la sala: " + sala.getNombre());

            boolean removed = sala.getUsuariosConectados().remove(usuario);
            if (!removed) {
                throw new IllegalStateException("⚠️ No se pudo eliminar al usuario de la lista de conectados");
            }

            usuario.setSala(null);
            salaRepository.save(sala);

            System.out.println("✅ Usuario removido de la sala con éxito");
        }
    }


    public int contarUsuariosConectados(Long salaId) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + salaId));

        return sala.getUsuariosConectados().size();
    }





}
