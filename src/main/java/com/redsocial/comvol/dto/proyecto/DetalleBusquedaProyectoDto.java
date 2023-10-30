package com.redsocial.comvol.dto.proyecto;

import com.redsocial.comvol.model.Rol;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetalleBusquedaProyectoDto {

    private Long idProyecto;
    private String tituloProyecto;
    private String descripcionProyecto;
    private String puestoSolicitado;
    private String descripcionCategoria;
    private String descripcionEstado;
    private int limitePersonasProyecto;
    private String urlImagenPersona;
    private String formaDePago;

    private List<String> rolesProyecto;

}
