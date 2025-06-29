package org.example.flowin2.domain.usuario.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.flowin2.domain.sala.model.Sala;

import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String mail;

    private String password;

    @ElementCollection
    private List<String> gustosMusicales;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ElementCollection
    private List<String> artistasFavoritos;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @OneToOne
    private Sala salaComoHost;
}
