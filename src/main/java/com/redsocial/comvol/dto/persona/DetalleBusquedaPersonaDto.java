package com.redsocial.comvol.dto.persona;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetalleBusquedaPersonaDto {

    private Long idPersona;
    private String nombre;
    private String apellido;
    private String email;
    private String pais;
    private String provincia;
    private String localidad;
    private String perfilExterno;
    private List<String> rol;
    private String suscripcion;

    private String numeroCelular;

    private String acercaDe;

}
