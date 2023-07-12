package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Persona;
import com.redsocial.comvol.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    @Query(value = "SELECT * FROM proyecto " +
            "WHERE id_categoria_proyecto IN (:listaCategorias) AND id_estado_proyecto IN (:listaEstados)",
            countQuery = "SELECT  COUNT(id_proyecto)  FROM proyecto " +
                    "WHERE id_categoria_proyecto IN (:listaCategorias) AND id_estado_proyecto IN (:listaEstados)",
            nativeQuery = true)
    Page<Proyecto> filtroProyecto(@Param("listaCategorias") List<Long> listaCategorias, @Param("listaEstados") List<Long> listaEstados, Pageable pagina);

    Page<Proyecto> findByIdResponsable(Persona idResponsable,Pageable pagina);


}
