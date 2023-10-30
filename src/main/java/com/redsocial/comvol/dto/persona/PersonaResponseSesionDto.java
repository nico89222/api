package com.redsocial.comvol.dto.persona;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonaResponseSesionDto {

    private Long id;
    private Integer tipoUsuario;

    private String esEmpresa;

}
