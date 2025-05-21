package com.project.FlowIn.Sala;

import lombok.Data;

import java.util.List;
@Data
public class SalaResponse {

    private String nombre;
    private List<String> género;
    private String artista;
    private List<String> canciones;
}
