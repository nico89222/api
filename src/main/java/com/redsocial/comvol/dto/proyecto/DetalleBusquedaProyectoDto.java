package com.redsocial.comvol.dto.proyecto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleBusquedaProyectoDto {

    private Long idProyecto;
    private String tituloProyecto;
    private String descripcionProyecto;
    private String descripcionCategoria;
    private String descripcionEstado;
    private int limitePersonasProyecto;
    private String urlImagenPersona;
    private String formaDePago;

}
