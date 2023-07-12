package com.redsocial.comvol.dto.personaProyecto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetallePostulacionPersonaDto {

    private Long idPersona;
    private Long idProyecto;
    private String tituloProyecto;
    private Long idPostulacionPersona;
    private String nombre;
    private String apellido;
    private String email;
    private String pais;
    private String provincia;
    private String localidad;
    private String perfilExterno;
    private Long idRol;
    private String rolPostulado;
    private String estadoPostulacion;
    private String imgProyecto;
    private String descripcionCategoria;
    private List<String> personaRoles;

}
