package com.redsocial.comvol.dto.personaProyecto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DetalleProyectoPersonaDto {

    private Long idPersonaProyecto;
    private Long idPersona;
    private Long idProyecto;
    private String nombre;
    private String apellido;
    private String email;
    private String rolPostulado;
    private LocalDate fechaAltaPersonaAlProyecto;
    private LocalDate fechaBajaPersonaAlProyecto;
    private String propietario;
    private String descripcionCategoria;
    private String urlImagenProyecto;
    private String tituloProyecto;
    private String descripcionEstado;
    private String numeroCelular;
    private String numeroCelularPropietario;

}
