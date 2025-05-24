package org.example.flowin2.domain.sala.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.flowin2.domain.chatMessage.ChatMessage;
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
    private List<String> genero;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVA;

    @ElementCollection
    private List<String> canciones;

    @OneToMany(mappedBy = "sala")
    private List<Usuario> usuariosConectados;
    @ManyToOne
    @JoinColumn(name = "host_id")
    //verificar esto
    private Usuario host;
    private Long idHost;

    @ElementCollection
    @CollectionTable(name = "chat_message", joinColumns = @JoinColumn(name = "sala_id"))
    @OrderColumn(name = "message_index")
    private List<ChatMessage> mensajesChat = new ArrayList<>();

}
