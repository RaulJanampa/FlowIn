package com.project.FlowIn.Usuario.Repository;

import com.project.FlowIn.Usuario.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
