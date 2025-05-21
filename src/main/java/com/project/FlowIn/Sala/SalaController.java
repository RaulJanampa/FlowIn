package com.project.FlowIn.Sala;

import com.project.FlowIn.Config.JWTService;
import com.project.FlowIn.Usuario.Domain.Usuario;
import com.project.FlowIn.Usuario.Infrastructure.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Importa la clase Authentication de Spring Security
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;
    @Autowired
    private JWTService jwtService;
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
}
