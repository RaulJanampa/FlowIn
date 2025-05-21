package com.project.FlowIn.Sala;

import com.project.FlowIn.Usuario.Domain.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public SalaResponse save(SalaRequest salaRequest, Usuario usuario) {
        // Mapea el objeto SalaRequest a Sala
        Sala sala = modelMapper.map(salaRequest, Sala.class);

        // Asocia la sala al usuario (suponiendo que un usuario puede tener una sala)
        sala.setUsuariosConectados(List.of(usuario)); // Agregar el usuario a la lista de usuarios conectados (si es necesario)
        usuario.setSala(sala);  // Asociamos la sala al usuario

        // Guarda la sala en el repositorio
        Sala salaGuardado = salaRepository.save(sala);

        // Devuelve la respuesta mapeada
        return modelMapper.map(salaGuardado, SalaResponse.class);
    }
}
