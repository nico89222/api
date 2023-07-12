package com.redsocial.comvol.controller;

import com.redsocial.comvol.dto.persona.DetalleBusquedaPersonaDto;
import com.redsocial.comvol.model.Persona;
import com.redsocial.comvol.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    PersonaRepository personaRepository;

    @GetMapping(value = "/test")
    public Page<Persona> filtroPorPersona(
            @RequestParam(name = "id_rol",required = false) List<Long> listaRolId,
            @RequestParam(name = "id_pais",required = false) List<Long> listaPaisId,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "20") Integer cantidad){

        //return personaRepository.filtroPersona(listaRolId == null || listaRolId.isEmpty(),listaRolId, listaPaisId == null ||listaPaisId.isEmpty(),listaPaisId, PageRequest.of(pagina,cantidad));

        return null;

    }
}
