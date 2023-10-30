package com.redsocial.comvol.dto.proyecto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@Validated
public class CrearProyectoDto {

    @NotNull
    private Long idResponsable;
    @NotNull
    private String tituloProyecto;
    @NotNull
    private String descripcionProyecto;
    @NotNull
    private String puestoSolicitado;
    @NotNull
    private Long idCategoriaProyecto;
    @NotNull
    private int limitePersonasProyecto;
    @NotNull
    private Long idEstado;
    private String urlImagenProyecto;
    private Long idFormaDePago;
    private boolean esEmpresa;
    private String razonSocial;
    private List<Long> proyectoRoles;


}
