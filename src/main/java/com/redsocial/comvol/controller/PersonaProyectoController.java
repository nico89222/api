package com.redsocial.comvol.controller;

import com.redsocial.comvol.dto.personaProyecto.*;
import com.redsocial.comvol.services.PersonaProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/persona_proyecto")
public class PersonaProyectoController {

    @Autowired
    private final PersonaProyectoService personaProyectoService;

    private static final String PRE_POSTULACION = "/postulacion";
    private static final String DETALLE_POSTULACION = "/detalle_postulacion";
    private static final String CREAR_PERSONA_PROYECTO = "/crear_persona_proyecto";
    private static final String RECHAZAR_POSTULACION_PERSONA = "/rechazar_persona_proyecto";
    private static final String DETALLE_PROYECTO_PERSONA = "/detalle_proyecto_persona";
    private static final String DETALLE_PROYECTO_INTEGRANTE = "/detalle_proyecto_integrante";
    private static final String DETALLE_POSTULACION_PERSONA = "/detalle_postulacion_persona";
    private static final String FINALIZAR_PERSONA_PROYECTO = "/finalizar_persona_proyecto";


    @PostMapping(value = PRE_POSTULACION, produces = "application/json")
    public PostulacionResponseDto postularPersona(@RequestParam(name = "id_persona") Long idPersona,
                                                  @RequestParam(name = "id_proyecto") Long idProyecto,
                                                  @RequestBody PersonaRolDto personaRolDto) throws MessagingException {

        return personaProyectoService.crearPostulacion(idPersona, idProyecto, personaRolDto);

    }

    @GetMapping(value = DETALLE_POSTULACION, produces = "application/json")
    public Page<DetallePostulacionPersonaDto> detallePostulacion(@RequestParam(name = "id_proyecto") Long idProyecto,
                                                                 @RequestParam(defaultValue = "0") Integer pagina,
                                                                 @RequestParam(defaultValue = "20") Integer cantidad) {

        return personaProyectoService.detallePostulacion(idProyecto, pagina, cantidad);
    }

    @PostMapping(value = CREAR_PERSONA_PROYECTO, produces = "application/json")
    public PersonaProyectoResponseDto crearPersonaProyecto(@RequestBody CrearPersonaProyectoDto crearPersonaProyectoDto) {

        return personaProyectoService.crearPersonaProyecto(crearPersonaProyectoDto);

    }

    @PatchMapping(value = RECHAZAR_POSTULACION_PERSONA, produces = "application/json")
    public PostulacionResponseDto cancelarPostulacion(@RequestParam(name = "id_persona_postulacion") Long idPersonaPostulacion,
                                                      @RequestBody PersonaEstadolDto personaEstadolDto) {
        return personaProyectoService.cancelarPostulacion(idPersonaPostulacion, personaEstadolDto);
    }

    @GetMapping(value = DETALLE_PROYECTO_PERSONA, produces = "application/json")
    public Page<DetalleProyectoPersonaDto> detalleProyectoPersona(@RequestParam(name = "id_proyecto") Long idProyecto,
                                                                  @RequestParam(defaultValue = "0") Integer pagina,
                                                                  @RequestParam(defaultValue = "20") Integer cantidad) {

        return personaProyectoService.detalleProyectoPersona(idProyecto, pagina, cantidad);
    }

    @GetMapping(value = DETALLE_PROYECTO_INTEGRANTE, produces = "application/json")
    public Page<DetalleProyectoPersonaDto> detalleProyectoIntegrante(@RequestParam(name = "id_persona") Long idPersona,
                                                                     @RequestParam(defaultValue = "0") Integer pagina,
                                                                     @RequestParam(defaultValue = "20") Integer cantidad) {

        return personaProyectoService.detalleProyectoPIntegrante(idPersona, pagina, cantidad);
    }

    @GetMapping(value = DETALLE_POSTULACION_PERSONA, produces = "application/json")
    public Page<DetallePostulacionPersonaDto> detallePostulacionPersona(@RequestParam(name = "id_persona") Long idPersona,
                                                                  @RequestParam(defaultValue = "0") Integer pagina,
                                                                  @RequestParam(defaultValue = "20") Integer cantidad) {

        return personaProyectoService.detallePostulacionPersona(idPersona, pagina, cantidad);
    }


    @PatchMapping(value = FINALIZAR_PERSONA_PROYECTO, produces = "application/json")
    public PersonaProyectoResponseDto bajaPersonaEnProyecto(@RequestParam(name = "id_persona_proyecto") Long idPersonaProyecto) {
        return personaProyectoService.bajaPersonaEnProyecto(idPersonaProyecto);
    }


}
