package com.project.FlowIn.Sala;

import com.project.FlowIn.Usuario.Domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //verificar si una sala deberia tener nombre
    private String nombre;

    @ElementCollection
    private List<String> género;

    private String artista;

    private String epoca;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ElementCollection
    private List<String> canciones;

    @OneToMany(mappedBy = "sala")
    private List<Usuario> usuariosConectados;
    //hoster de la sala (dj)
    //private Host host;
}
