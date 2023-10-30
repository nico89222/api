package com.redsocial.comvol.converter;

import com.redsocial.comvol.dto.persona.DetallePersonaDto;
import com.redsocial.comvol.model.Persona;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PersonaToDetallePersonaDtoConverter implements Converter<Persona, DetallePersonaDto> {


    @Override
    public DetallePersonaDto convert(Persona persona) {
        List<String> listadoRoles = new ArrayList<>();

        persona.getListaRoles().forEach(rol -> listadoRoles.add(rol.getDescripcionRol()));

        return DetallePersonaDto.builder()
                .idPersona(persona.getIdPersona())
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .email(persona.getEmail())
                .acercaDe(persona.getAcercaDe())
                .urlImagenPersona(persona.getUrlImagenPersona())
                .suscripcion(persona.isSuscripcion())
                .idPais(persona.getPais().getIdPais())
                .pais(persona.getPais().getDescripcionPais())
                .provincia(persona.getProvincia())
                .localidad(persona.getLocalidad())
                .perfilExterno(persona.getPerfilExterno())
                .personaRoles(listadoRoles)
                .roles(persona.getListaRoles())
                .descripcionCategoria(persona.getCategoria().getDescripcionCategoria())
                .idCategoria(persona.getCategoria().getIdCategoria())
                .esEmpresa(persona.getEsEmpresa())
                .numeroCelular(persona.getNumeroCelular())
                .build();
    }
}
