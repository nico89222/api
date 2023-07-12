package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.EstadoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPersonaRepository extends JpaRepository<EstadoPersona,Long> {

}
