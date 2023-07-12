package com.redsocial.comvol.dto.persona;

import com.redsocial.comvol.model.Rol;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetallePersonaDto {

    private Long idPersona;
    private String nombre;
    private String apellido;
    private String email;
    private String acercaDe;
    private String urlImagenPersona;
    private boolean suscripcion;
    private Long idPais;
    private String pais;
    private String provincia;
    private String localidad;
    private String perfilExterno;
    private List<String> personaRoles;
    private List<Rol> roles;



}
