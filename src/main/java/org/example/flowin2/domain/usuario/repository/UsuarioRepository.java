package org.example.flowin2.domain.usuario.repository;

import org.example.flowin2.domain.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByUsername(String username);
    // UsuarioRepository.java debemos cambiar el code para usar el optional
    //Optional<Usuario> findByUsername(String username);
}
