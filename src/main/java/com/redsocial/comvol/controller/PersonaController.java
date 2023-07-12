package com.redsocial.comvol.controller;

import com.redsocial.comvol.dto.persona.*;
import com.redsocial.comvol.services.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/persona")
public class PersonaController {

    private static final String DETALLE_PERSONA = "/detalle";
    private static final String CREAR_PERSONA = "/crear";
    private static final String EDITAR_PERSONA = "/editar";
    private static final String BAJA_PERSONA = "/baja";
    private static final String ALTA_SUSCRIPCION_PERSONA = "/alta_suscripcion";
    private static final String BAJA_SUSCRIPCION_PERSONA = "/baja_suscripcion";
    private static final String FILTRO_PERSONA = "/filtrar";
    private static final String MODIFICAR_CONTRASENA = "/modificar_contrasena";
    private static final String ACTIVACION_PERSONA = "/activar_usuario";
    private static final String INICIO_SESION = "/inicio_sesion";

    @Autowired
    private final PersonaService personaService;


    @PostMapping(value = CREAR_PERSONA, produces = "application/json")
    public PersonaResponseDto crearPersona(@RequestBody CrearPersonaDto crearPersonaDto) {

        return personaService.crearPersona(crearPersonaDto);

    }

    @GetMapping(value = DETALLE_PERSONA, produces = "application/json")
    public DetallePersonaDto detalle(@RequestParam(name = "id_persona") Long idPersona) {

        return personaService.detallePersona(idPersona);
    }

    @PatchMapping(value = EDITAR_PERSONA, produces = "application/json")
    public DetallePersonaDto editar(@RequestParam(name = "id_persona") Long idPersona, @RequestBody EditarPersonaDto editarPersonaDto) {

        return personaService.editarPersona(idPersona, editarPersonaDto);
    }

    @PatchMapping(value = BAJA_PERSONA, produces = "application/json")
    public PersonaResponseDto baja(@RequestParam(name = "id_persona") Long idPersona) {
        return personaService.bajaPersona(idPersona);
    }

    @PatchMapping(value = ALTA_SUSCRIPCION_PERSONA, produces = "application/json")
    public PersonaResponseDto altaSuscripcion(@RequestParam(name = "id_persona") Long idPersona) {
        return personaService.altaSuscripcionPersona(idPersona);
    }

    @PatchMapping(value = BAJA_SUSCRIPCION_PERSONA, produces = "application/json")
    public PersonaResponseDto bajaSuscripcion(@RequestParam(name = "id_persona") Long idPersona) {
        return personaService.bajaSuscripcion(idPersona);
    }

    @GetMapping(value = FILTRO_PERSONA, produces = "application/json")
    public ResponseEntity<Page<DetalleBusquedaPersonaDto>> filtroPorPersona(
            @RequestParam(name = "id_rol") List<Long> listaRolId,
            @RequestParam(name = "id_pais") List<Long> listaPaisId,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "20") Integer cantidad) {

        Page<DetalleBusquedaPersonaDto> listaPersonasFiltrada = personaService.listaPersona(listaRolId, listaPaisId, pagina, cantidad);

        return ResponseEntity.ok(listaPersonasFiltrada);


    }

    @PatchMapping(value = MODIFICAR_CONTRASENA, produces = "application/json")
    public PersonaResponseDto recuperarContrasena(@RequestParam(name = "id_persona") Long idPersona,
                                                  @RequestBody CambioContrasenaDto cambioContrasenaDto) {

        return personaService.modificarContrasena(idPersona, cambioContrasenaDto);


    }



    @PatchMapping(value = ACTIVACION_PERSONA, produces = "application/json")
    public PersonaResponseDto activarPersona(@RequestParam(name = "id_persona") Long idPersona){

        return personaService.activarUsuario(idPersona);

    }

    @PostMapping(value = INICIO_SESION, produces = "application/json")
    public PersonaResponseSesionDto validaContrasena(@RequestBody InicioSesionDto inicioSesionDto) {

        return personaService.chequeaContrasena(inicioSesionDto);
    }

}
