package com.project.FlowIn.Usuario.Infrastructure;

import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Domain.UsuarioRequest;
import com.project.FlowIn.Usuario.Domain.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrarse")
    public ResponseEntity<UsuarioResponse> signUpUsuario(
            @RequestBody @Validated UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.save(usuarioRequest);
        return ResponseEntity.ok(usuarioResponse);
    }
}
