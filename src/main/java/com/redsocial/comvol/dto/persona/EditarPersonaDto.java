package com.redsocial.comvol.dto.persona;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
public class EditarPersonaDto {

    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @Nullable
    private String acercaDe;
    @NotNull
    private String provincia;
    @NotNull
    private String localidad;
    private String perfilExterno;
    private List<Long> personaRoles;

}
