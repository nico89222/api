package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Persona;
import com.redsocial.comvol.model.PersonaProyecto;
import com.redsocial.comvol.model.PostulacionPersona;
import com.redsocial.comvol.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaProyectoRepository extends JpaRepository<PersonaProyecto,Long> {

    Optional<PersonaProyecto> findByProyectoAndPersona(Proyecto proyecto, Persona persona);

    Page<PersonaProyecto> findByProyecto(Proyecto Proyecto, Pageable pagina);

    Page<PersonaProyecto> findByPersona (Persona persona, Pageable pagina);

    List<PersonaProyecto> findByProyecto(Proyecto Proyecto);

}
