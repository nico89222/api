package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Persona;
import com.redsocial.comvol.model.PostulacionPersona;
import com.redsocial.comvol.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostulacionPersonaRepository extends JpaRepository<PostulacionPersona, Long> {

    Optional<PostulacionPersona> findByIdProyectoAndIdPersona(Proyecto idProyecto, Persona idPersona);

    Page<PostulacionPersona> findByIdProyecto(Proyecto idProyecto, Pageable pagina);

    Page<PostulacionPersona> findByIdPersona(Persona idPersona, Pageable pagina);

}
