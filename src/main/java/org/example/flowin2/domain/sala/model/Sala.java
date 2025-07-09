package org.example.flowin2.domain.sala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.example.flowin2.domain.chatMessage.model.ChatMessage;
import org.example.flowin2.domain.usuario.model.Usuario;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ElementCollection
    @JsonIgnore
    private List<String> genero;

    private String artista;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVA;

    private String canciones;

    @OneToMany(mappedBy = "sala")
    private List<Usuario> usuariosConectados;

    @ManyToOne
    @JoinColumn(name = "host_id")
    @JsonManagedReference
    private Usuario host;

    private String cancionActual;
    private Boolean reproduciendo = false;
    private Long timestampInicio;

    // Relación uno a muchos con ChatMessage
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ChatMessage> mensajesChat;
}
