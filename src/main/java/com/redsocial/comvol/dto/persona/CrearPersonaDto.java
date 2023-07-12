package com.redsocial.comvol.dto.persona;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class CrearPersonaDto {


    @NotNull
    private String nombre;

    private String apellido;
    @NotNull
    private String email;
    @Nullable
    private String acercaDe;
    @NotNull
    private String contrasena;
    @NotNull
    private String recontrasena;
    @NotNull
    private boolean suscripcion;
    @NotNull
    private Long pais;
    @NotNull
    private String provincia;
    @NotNull
    private String localidad;
    private String perfilExterno;
    private List<Long> personaRoles;




}
