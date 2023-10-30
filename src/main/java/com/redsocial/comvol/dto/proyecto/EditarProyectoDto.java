package com.redsocial.comvol.dto.proyecto;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
public class EditarProyectoDto {


    @NotNull
    private String tituloProyecto;
    @NotNull
    private String descripcionProyecto;
    @NotNull
    private String puestoSolicitado;
    @NotNull
    private Long idCategoriaProyecto;
    @NotNull
    private Long idEstadoProyecto;
    @NotNull
    private int limitePersonasProyecto;

    private String urlImagenProyecto;
    private Long idFormaDePago;
    private boolean esEmpresa;
    private String razonSocial;
    private List<Long> proyectoRoles;

}
