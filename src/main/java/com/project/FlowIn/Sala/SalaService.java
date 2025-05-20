package com.project.FlowIn.Sala;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public SalaResponse save(SalaRequest salaRequest) {
        Sala sala = modelMapper.map(salaRequest, Sala.class);

        Sala salaGuardado = salaRepository.save(sala);

        return modelMapper.map(salaGuardado, SalaResponse.class);
    }
}
