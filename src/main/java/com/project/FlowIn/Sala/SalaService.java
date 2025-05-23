package com.project.FlowIn.Sala;

import com.project.FlowIn.Usuario.Domain.Tipo;
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
        Sala sala = modelMapper.map(salaRequest, Sala.class);

        // Asignar host
        sala.setHost(usuario);
        sala.setIdHost(usuario.getId());

        // Setear usuario como host temporal
        usuario.setTipo(Tipo.HOST);
        usuario.setSalaComoHost(sala);

        // Agregarlo también a los conectados (opcional)
        sala.setUsuariosConectados(List.of(usuario));

        // Guardar la sala
        Sala salaGuardada = salaRepository.save(sala);

        // Podrías guardar el usuario actualizado también si tu JPA no lo hace automáticamente
        // usuarioRepository.save(usuario); <-- opcional si cascade no está configurado

        return modelMapper.map(salaGuardada, SalaResponse.class);
    }

}
