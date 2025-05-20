package com.project.FlowIn.Usuario.Infrastructure;

import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Domain.UsuarioRequest;
import com.project.FlowIn.Usuario.Domain.UsuarioResponse;
import com.project.FlowIn.Usuario.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioResponse save(UsuarioRequest usuario) {
        UsuarioResponse newusuario = new UsuarioResponse();
        return newusuario;
    };
}
