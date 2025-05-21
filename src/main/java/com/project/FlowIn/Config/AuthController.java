package com.project.FlowIn.Config;

import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Infrastructure.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // Aquí debes validar las credenciales del usuario
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
