package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByEmail(String email);

    /*
        @Query(value = "SELECT p.* , pr.id_rol FROM persona p " +
                "LEFT JOIN persona_rol pr on p.id_persona = pr.id_persona" +
                "WHERE pr.id_rol in (:listaRoles) AND p.id_pais IN (:listaPaises)"+
                "ORDER BY pr.id_rol ",
                nativeQuery = true)

                */
    @Query(value = "SELECT p FROM Persona p " +
            "JOIN p.listaRoles rol JOIN p.pais pais " +
            "WHERE rol.id IN (:listaRoles) " +
            "AND pais.id IN (:listaPaises) " +
            "ORDER BY p.idPersona")
    Page<Persona> filtroPersona(@Param("listaRoles") List<Long> listaRolId,
                                @Param("listaPaises") List<Long> listaPaisId, Pageable pagina);


    /*

    @Query(value = "FROM Persona p " +
            "JOIN p.listaRoles rol JOIN p.pais pais " +
            "WHERE (:ignorarFiltroRoles = true or rol.id IN (:listaRoles)) " +
            "AND (:ignorarFiltroPaises = true or pais.id IN (:listaPaises)) " +
            "ORDER BY p.idPersona")
    Page<Persona> filtroPersona(@Param("ignorarFiltroRoles") boolean ignorarFiltroRoles,
                                @Param("listaRoles") List<Long> listaRolId,
                                @Param("ignorarFiltroPaises") boolean ignorarFiltroPaises,
                                @Param("listaPaises") List<Long> listaPaisId, Pageable pagina);

//SELECT p.nombre,p.apellido,pais.descripcionPais, p.provincia,p.localidad, rol.descripcionRol

     */
}
