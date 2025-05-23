package org.example.flowin2.web.dto.usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.flowin2.domain.usuario.model.Tipo;

import java.util.List;

@Data
public class UsuarioRequest {
    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank @Email @Column(unique = true)
    private String mail;
    @NotBlank
    private String password;
    //Puede que no quiera dar sus gustos
    private List<String> gustosMusicales;
    //hay que ver como hacemos para el login de un usuario y traerlo de Security
    private Tipo tipo = Tipo.USUARIO;
    //Puede ser nulo
    private List<String> artistasFavoritos;
}