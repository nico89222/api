package com.redsocial.comvol.dto.persona;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CambioContrasenaDto {

    @NotNull
    private String nuevaContrasena;
    @NotNull
    private String nuevaReContrasena;
}
