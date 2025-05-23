package org.example.flowin2.application.usuario;

import org.example.flowin2.domain.usuario.model.Usuario;
import org.example.flowin2.domain.usuario.repository.UsuarioRepository;
import org.example.flowin2.infrastructure.security.JwtService;
import org.example.flowin2.web.dto.usuario.UsuarioRequest;
import org.example.flowin2.web.dto.usuario.UsuarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        //falta guardar la contraseña encriptada -- listo
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return modelMapper.map(usuarioGuardado, UsuarioResponse.class);
    }
    public Optional<Usuario> findByUsername(String username) { return usuarioRepository.findByUsername(username); }

    public UsuarioResponse obtenerPerfil(String token) {
        String username = jwtService.extractUserName(token.substring(7));
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapUsuarioToResponse(usuario);
    }
    private UsuarioResponse mapUsuarioToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setMail(usuario.getMail());
        response.setTipo(usuario.getTipo());
        response.setGustosMusicales(usuario.getGustosMusicales());
        response.setArtistasFavoritos(usuario.getArtistasFavoritos());
        return response;
    }


}