package com.project.FlowIn.Usuario.Infrastructure;

import com.project.FlowIn.Config.JWTService;
import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Domain.UsuarioRequest;
import com.project.FlowIn.Usuario.Domain.UsuarioResponse;
import com.project.FlowIn.Usuario.Repository.UsuarioRepository;
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
    private JWTService jwtService;

    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        //falta guardar la contraseña encriptada -- listo
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return modelMapper.map(usuarioGuardado, UsuarioResponse.class);
    }
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public UsuarioResponse obtenerPerfil(String token) {
        String username = jwtService.extractUserName(token.substring(7));
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByUsername(username))
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