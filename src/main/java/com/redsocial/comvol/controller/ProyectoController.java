package com.redsocial.comvol.controller;

import com.redsocial.comvol.dto.proyecto.*;
import com.redsocial.comvol.services.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/proyecto")
public class ProyectoController {

    private static final String CREAR_PROYECTO = "/crear";
    private static final String DETALLE_PROYECTO = "/detalle";
    private static final String LISTA_DETALLE_PROYECTO = "/lista_detalle";
    private static final String EDITAR_PROYECTO = "/editar";
    private static final String FILTRO_PROYECTO = "/filtrar";


    @Autowired
    private final ProyectoService proyectoService;


    @PostMapping(value = CREAR_PROYECTO, produces = "application/json")
    public ProyectoResponseDto crearProyecto(@RequestBody CrearProyectoDto crearProyectoDto) {

        return proyectoService.crearProyecto(crearProyectoDto);

    }


    @GetMapping(value = DETALLE_PROYECTO, produces = "application/json")
    public DetalleProyectoDto detalle(@RequestParam(name = "id_proyecto") Long idProyecto) {

        return proyectoService.detalleProyecto(idProyecto);
    }

    @GetMapping(value = LISTA_DETALLE_PROYECTO, produces = "application/json")
    public Page<DetalleProyectoDto> listaDetalle(@RequestParam(name = "id_persona") Long idPersona,
                                                 @RequestParam(defaultValue = "0") Integer pagina,
                                                 @RequestParam(defaultValue = "20") Integer cantidad) {

        return proyectoService.listaDetalleProyecto(idPersona,cantidad,pagina);
    }


    @PatchMapping(value = EDITAR_PROYECTO, produces = "application/json")
    public DetalleProyectoDto editar(@RequestParam(name = "id_proyecto") Long idProyecto, @RequestBody EditarProyectoDto editarProyectoDto) {

        return proyectoService.editarProyecto(idProyecto, editarProyectoDto);
    }

    @GetMapping(value = FILTRO_PROYECTO, produces = "application/json")
    public ResponseEntity<Page<DetalleBusquedaProyectoDto>> filtroPorProyecto(
            @RequestParam(name = "id_categoria") List<Long> listaCategoriaId,
            @RequestParam(name = "id_estado") List<Long> listaEstadoId,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "20") Integer cantidad) {

        Page<DetalleBusquedaProyectoDto> listaProyectosFiltrada = proyectoService.listaProyectos(listaCategoriaId, listaEstadoId, pagina, cantidad);

        return ResponseEntity.ok(listaProyectosFiltrada);


    }


}
