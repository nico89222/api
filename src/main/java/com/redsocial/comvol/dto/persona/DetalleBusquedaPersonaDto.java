package com.redsocial.comvol.dto.persona;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetalleBusquedaPersonaDto {


    private String nombre;
    private String apellido;
    private String email;
    private String pais;
    private String provincia;
    private String localidad;
    private String perfilExterno;
    private List<String> rol;
    private String suscripcion;

}
