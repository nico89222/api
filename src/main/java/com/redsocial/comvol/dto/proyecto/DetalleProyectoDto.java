package com.redsocial.comvol.dto.proyecto;

import com.redsocial.comvol.model.Rol;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetalleProyectoDto {


    private Long idProyecto;
    private Long idReferente;
    private String nombreReferente;
    private String tituloProyecto;
    private Long idCategoria;
    private String descripcionProyecto;
    private String puestoSolicitado;
    private String descripcionCategoria;
    private Long idEstado;
    private String descripcionEstado;
    private int limitePersonasProyecto;
    private String urlImagenProyecto;
    private Long idFormaDePago;
    private String formaDePago;
    private boolean esEmpresa;
    private String razonSocial;
    private List<Rol> proyectoRoles;


}
