package com.redsocial.comvol.dto.proyecto;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class EditarProyectoDto {


    @NotNull
    private String tituloProyecto;
    @NotNull
    private String descripcionProyecto;
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

}
