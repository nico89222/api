package com.redsocial.comvol.dto.personaProyecto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonaRolDto {


    @NotNull
    private Long idRol;


}
