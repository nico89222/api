package com.redsocial.comvol.dto.persona;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InicioSesionDto {

    @NotNull
    private String email;
    @NotNull
    private String contrasena;
}
