package com.redsocial.comvol.services;

import com.redsocial.comvol.converter.ProyectoToDetalleProyectoDtoConverter;
import com.redsocial.comvol.dto.proyecto.*;
import com.redsocial.comvol.exception.BadRequestException;
import com.redsocial.comvol.model.*;
import com.redsocial.comvol.repository.*;
import com.redsocial.comvol.utils.Mensajes;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final CategoriaRepository categoriaRepository;
    private final ProyectoRepository proyectoRepository;
    private final EstadoRepository estadoRepository;
    private final PersonaService personaService;
    private final FormaDePagoRepository formaDePagoRepository;
    private final PersonaProyectoRepository personaProyectoRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;


    private final ProyectoToDetalleProyectoDtoConverter proyectoToDetalleProyectoDtoConverter;

    public ProyectoResponseDto crearProyecto(CrearProyectoDto crearProyectoDto) {

        return proyectoResponse(guardarProyecto(mapearProyecto(crearProyectoDto)));
    }

    private Proyecto mapearProyecto(CrearProyectoDto crearProyectoDto) {

        Persona persona = personaService.buscarPersona(crearProyectoDto.getIdResponsable());
        Estado estado = estadoRepository.findById(1L).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO));
        Categoria categoria = categoriaRepository.findById(crearProyectoDto.getIdCategoriaProyecto()).orElseThrow(() -> new BadRequestException(Mensajes.CATEGORIA_NO_ENCONTRADO));
        FormaDePago formaDePago = formaDePagoRepository.findById(crearProyectoDto.getIdFormaDePago()).orElseThrow(() -> new BadRequestException(Mensajes.FORMA_DE_PAGO_NO_ENCONTRADO));
        List<Rol> listaRolesProyecto = rolRepository.findAllById(crearProyectoDto.getProyectoRoles());

        return Proyecto.builder()
                .descripcionProyecto(crearProyectoDto.getDescripcionProyecto())
                .puestoSolicitado(crearProyectoDto.getPuestoSolicitado())
                .tituloProyecto(crearProyectoDto.getTituloProyecto())
                .estadoProyecto(estado)
                .categoriaProyecto(categoria)
                .limitePersonasProyecto(crearProyectoDto.getLimitePersonasProyecto())
                .urlImagenProyecto(crearProyectoDto.getUrlImagenProyecto())
                .idResponsable(persona)
                .formaDePago(formaDePago)
                .esEmpresa(crearProyectoDto.isEsEmpresa())
                .razonSocial(crearProyectoDto.getRazonSocial())
                .fechaAltaProyecto(LocalDate.now())
                .listaRolesProyecto(listaRolesProyecto)
                .build();

    }

    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    private ProyectoResponseDto proyectoResponse(Proyecto proyecto) {

        return ProyectoResponseDto.builder()
                .id(proyecto.getIdProyecto())
                .build();
    }

    public DetalleProyectoDto detalleProyecto(Long idProyecto) {


        Proyecto proyecto = buscarProyecto(idProyecto);

        return proyectoToDetalleProyectoDtoConverter.convert(proyecto);
    }

    public Proyecto buscarProyecto(Long idProyecto) {

        return proyectoRepository.findById(idProyecto).orElseThrow(() -> new BadRequestException(Mensajes.PROYECTO_NO_ENCONTRADO));
    }

    public DetalleProyectoDto editarProyecto(Long idProyecto, EditarProyectoDto editarPersonaDto) {

        Proyecto proyecto = buscarProyecto(idProyecto);

        return proyectoToDetalleProyectoDtoConverter.convert(guardarProyecto(mapearEditarProyecto(proyecto, editarPersonaDto)));

    }

    private Proyecto mapearEditarProyecto(Proyecto proyecto, EditarProyectoDto editarProyectoDto) {

        Categoria categoria = categoriaRepository.findById(editarProyectoDto.getIdCategoriaProyecto()).orElseThrow(() -> new BadRequestException(Mensajes.CATEGORIA_NO_ENCONTRADO));
        Estado estado = estadoRepository.findById(editarProyectoDto.getIdEstadoProyecto()).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO));
        FormaDePago formaDePago = formaDePagoRepository.findById(editarProyectoDto.getIdFormaDePago()).orElseThrow(() -> new BadRequestException(Mensajes.FORMA_DE_PAGO_NO_ENCONTRADO));
        List<Rol> listaRoles = rolRepository.findAllById(editarProyectoDto.getProyectoRoles());


        proyecto.setTituloProyecto(editarProyectoDto.getTituloProyecto());
        proyecto.setDescripcionProyecto(editarProyectoDto.getDescripcionProyecto());
        proyecto.setPuestoSolicitado(editarProyectoDto.getPuestoSolicitado());
        proyecto.setCategoriaProyecto(categoria);
        proyecto.setEstadoProyecto(estado);
        proyecto.setLimitePersonasProyecto(editarProyectoDto.getLimitePersonasProyecto());
        proyecto.setUrlImagenProyecto(editarProyectoDto.getUrlImagenProyecto());
        proyecto.setFormaDePago(formaDePago);
        proyecto.setEsEmpresa(editarProyectoDto.isEsEmpresa());
        proyecto.setRazonSocial(editarProyectoDto.getRazonSocial());
        proyecto.setListaRolesProyecto(listaRoles);

        if (proyecto.getFechaBajaProyecto() == null && estado.getIdEstado() == 3 || estado.getIdEstado() == 4) {

            List<PersonaProyecto> personaProyecto = personaProyectoRepository.findByProyecto(proyecto);
            personaProyecto.stream().filter(pp -> pp.getFechaBajaPersonaAlProyecto() == null).forEach(p -> p.setFechaBajaPersonaAlProyecto(LocalDate.now()));

            proyecto.setFechaBajaProyecto(LocalDate.now());
        }

        return proyecto;
    }


    public Page<DetalleBusquedaProyectoDto> listaProyectos(List<Long> listaCategoriaId, List<Long> listaEstadoId, List <Long> listaRolId, Integer pagina, Integer cantidad) {

        listaCategoriaId = eliminarNulosLista(listaCategoriaId);

        listaEstadoId = eliminarNulosLista(listaEstadoId);

        listaRolId = eliminarNulosLista(listaRolId);

        validarCamposFiltro(listaCategoriaId, listaEstadoId, listaRolId);

        Page<Proyecto> resultadoPaginado = proyectoRepository.filtroProyecto(listaCategoriaId, listaEstadoId, listaRolId, PageRequest.of(pagina, cantidad));


        return mapearResultadoFiltro(resultadoPaginado);
    }


    private List<Long> eliminarNulosLista(List<Long> listaCategoriaEstado) {

        if (listaCategoriaEstado.contains(null))
            return listaCategoriaEstado.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return listaCategoriaEstado;

    }

    private void validarCamposFiltro(List<Long> listaCategoriaId, List<Long> listaEstadoId, List<Long> listaRolId) {

        List<Long> listaCategoriaExistente = categoriaRepository.findAll().stream().map(Categoria::getIdCategoria).collect(Collectors.toList());

        List<Long> listaEstadoExistente = estadoRepository.findAll().stream().map(Estado::getIdEstado).collect(Collectors.toList());

        List<Long> listaRolExistente = rolRepository.findAll().stream().map(Rol::getIdRol).collect(Collectors.toList());

        validarExisteCampos(listaCategoriaId, listaEstadoId, listaRolId, listaCategoriaExistente, listaEstadoExistente,listaRolExistente);

        completaListasVaciasPorDefecto(listaCategoriaId, listaEstadoId, listaRolId, listaCategoriaExistente, listaEstadoExistente,listaRolExistente);
    }

    private void completaListasVaciasPorDefecto(List<Long> listaCategoriaId, List<Long> listaEstadoId, List<Long> listaRolId, List<Long> listaCategoriaExistente, List<Long> listaEstadoExistente, List<Long> listaRolExistente) {

        if (listaCategoriaId.isEmpty()) {
            listaCategoriaId.addAll(listaCategoriaExistente);
        }

        if (listaEstadoId.isEmpty()) {
            listaEstadoId.addAll(listaEstadoExistente);
        }

        if (listaRolId.isEmpty()) {
            listaRolId.addAll(listaRolExistente);
        }
    }

    private void validarExisteCampos(List<Long> listaCategoriaId, List<Long> listaEstadoId, List<Long> listaRolId, List<Long> listaCategoriaExistente, List<Long> listaEstadoExistente, List<Long> listaRolExistente ) {

        List<Long> listaCategoriasNoEncontrados = new ArrayList<>();
        List<Long> listaEstadosNoEncontrados = new ArrayList<>();
        List<Long> listaRolNoEncontrados = new ArrayList<>();

        if (!listaCategoriaId.isEmpty())
            listaCategoriasNoEncontrados = listarDatosNoEncontrados(listaCategoriaId, listaCategoriaExistente);

        if (!listaEstadoId.isEmpty())
            listaEstadosNoEncontrados = listarDatosNoEncontrados(listaEstadoId, listaEstadoExistente);

        if (!listaRolId.isEmpty())
            listaRolNoEncontrados = listarDatosNoEncontrados(listaRolId, listaRolExistente);

        camposNoEncontrados(listaCategoriasNoEncontrados, listaEstadosNoEncontrados,listaRolNoEncontrados);
    }


    private List<Long> listarDatosNoEncontrados(List<Long> listaCamposId, List<Long> listaDatosExistente) {
        return listaCamposId.stream().filter(campo -> listaDatosExistente.stream().noneMatch(campo::equals)).collect(Collectors.toList());
    }

    private void camposNoEncontrados(List<Long> listaCategoriasNoEncontrados, List<Long> listaEstadosNoEncontrados, List <Long> listaRolNoEncontrados) {

        if (!listaCategoriasNoEncontrados.isEmpty())
            throw new BadRequestException(Mensajes.CATEGORIA_NO_ENCONTRADO + listaCategoriasNoEncontrados);

        if (!listaEstadosNoEncontrados.isEmpty())
            throw new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO + listaEstadosNoEncontrados);

        if (!listaRolNoEncontrados.isEmpty())
            throw new BadRequestException(Mensajes.ROL_NO_ENCONTRADO + listaRolNoEncontrados);

    }

    private Page<DetalleBusquedaProyectoDto> mapearResultadoFiltro(Page<Proyecto> resultadoPaginado) {

        return resultadoPaginado.map(proyecto -> DetalleBusquedaProyectoDto.builder()
                .idProyecto(proyecto.getIdProyecto())
                .tituloProyecto(proyecto.getTituloProyecto())
                .descripcionProyecto(proyecto.getDescripcionProyecto())
                .puestoSolicitado(proyecto.getPuestoSolicitado())
                .descripcionCategoria(proyecto.getCategoriaProyecto().getDescripcionCategoria())
                .descripcionEstado(proyecto.getEstadoProyecto().getDescripcionEstado())
                .formaDePago(proyecto.getFormaDePago().getDescripcionFormaDePago())
                .limitePersonasProyecto(proyecto.getLimitePersonasProyecto())
                .urlImagenPersona(proyecto.getUrlImagenProyecto())
                .rolesProyecto(proyecto.getListaRolesProyecto().stream().map(p -> p.getDescripcionRol()).collect(Collectors.toList()))
                .build());
    }


    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public Page<DetalleProyectoDto> listaDetalleProyecto(Long idPersona,Integer cantidad, Integer pagina) {

        Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_NO_ENCONTRADA));

        Page<Proyecto> proyecto = proyectoRepository.findByIdResponsable(persona, PageRequest.of(pagina, cantidad));

        return proyecto.map(this::mapearListadoProyecto);


    }

    DetalleProyectoDto mapearListadoProyecto(Proyecto proyecto) {

        return  DetalleProyectoDto.builder()
                        .idProyecto(proyecto.getIdProyecto())
                        .nombreReferente(proyecto.getIdResponsable().getNombre())
                        .tituloProyecto(proyecto.getTituloProyecto())
                        .descripcionProyecto(proyecto.getDescripcionProyecto())
                        .descripcionCategoria(proyecto.getCategoriaProyecto().getDescripcionCategoria())
                        .descripcionEstado(proyecto.getEstadoProyecto().getDescripcionEstado())
                        .limitePersonasProyecto(proyecto.getLimitePersonasProyecto())
                        .urlImagenProyecto(proyecto.getUrlImagenProyecto())
                        .formaDePago(proyecto.getFormaDePago().getDescripcionFormaDePago())
                        .esEmpresa(proyecto.isEsEmpresa())
                        .razonSocial(proyecto.getRazonSocial())
                        .build();

    }

}
