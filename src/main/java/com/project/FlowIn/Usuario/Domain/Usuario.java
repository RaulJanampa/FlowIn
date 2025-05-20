package com.project.FlowIn.Usuario.Domain;

import com.project.FlowIn.Sala.Sala;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
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
}
