package com.redsocial.comvol.converter;

import com.redsocial.comvol.dto.proyecto.DetalleProyectoDto;
import com.redsocial.comvol.model.Proyecto;
import com.redsocial.comvol.model.Rol;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProyectoToDetalleProyectoDtoConverter implements Converter<Proyecto, DetalleProyectoDto> {


    @Override
    public DetalleProyectoDto convert(Proyecto proyecto) {

        return DetalleProyectoDto.builder()
                .idProyecto(proyecto.getIdProyecto())
                .descripcionProyecto(proyecto.getDescripcionProyecto())
                .puestoSolicitado(proyecto.getPuestoSolicitado())
                .tituloProyecto(proyecto.getTituloProyecto())
                .idCategoria(proyecto.getCategoriaProyecto().getIdCategoria())
                .descripcionCategoria(proyecto.getCategoriaProyecto().getDescripcionCategoria())
                .idEstado(proyecto.getEstadoProyecto().getIdEstado())
                .descripcionEstado(proyecto.getEstadoProyecto().getDescripcionEstado())
                .limitePersonasProyecto(proyecto.getLimitePersonasProyecto())
                .urlImagenProyecto(proyecto.getUrlImagenProyecto())
                .idReferente(proyecto.getIdResponsable().getIdPersona())
                .nombreReferente(proyecto.getIdResponsable().getNombre() + " " + proyecto.getIdResponsable().getApellido())
                .idFormaDePago(proyecto.getFormaDePago().getIdFormaDePago())
                .formaDePago(proyecto.getFormaDePago().getDescripcionFormaDePago())
                .esEmpresa(proyecto.isEsEmpresa())
                .razonSocial(proyecto.getRazonSocial())
                .proyectoRoles(proyecto.getListaRolesProyecto())
                .build();
    }
}
