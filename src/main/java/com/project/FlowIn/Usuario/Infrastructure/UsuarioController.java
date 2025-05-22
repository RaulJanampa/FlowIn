package com.project.FlowIn.Usuario.Infrastructure;

import com.project.FlowIn.Config.JWTService;
import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Domain.UsuarioRequest;
import com.project.FlowIn.Usuario.Domain.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/registrarse")
    public ResponseEntity<?> signUpUsuario(@RequestBody @Validated UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.save(usuarioRequest);

        Usuario usuario = usuarioService.findByUsername(usuarioRequest.getUsername());
        String token = jwtService.generateToken(usuario); // <-- usar el nuevo método

        return ResponseEntity.ok("Bearer " + token);
    }
}
