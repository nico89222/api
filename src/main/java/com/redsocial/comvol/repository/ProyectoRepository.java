package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Persona;
import com.redsocial.comvol.model.PersonaProyecto;
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

    @Query(value = "SELECT DISTINCT p.id_proyecto ,p.descripcion_proyecto , p.limite_personas_proyecto , p.titulo_proyecto, " +
            "p.url_imagen_proyecto , p.id_categoria_proyecto , p.id_estado_proyecto, p.id_forma_pago, p.id_responsable , p.puesto_solicitado , p.es_empresa , " +
            "p.fecha_alta_proyecto , p.fecha_baja_proyecto , p.razon_social " +
            "FROM proyecto p " +
            "LEFT JOIN proyecto_rol pr " +
            "ON p.id_proyecto = pr.id_proyecto "+
            "WHERE p.id_categoria_proyecto IN (:listaCategorias) AND p.id_estado_proyecto IN (:listaEstados) "+
            "AND pr.id_rol IN (:listaRolId) ",
            countQuery = "SELECT COUNT(DISTINCT p.id_proyecto)  FROM proyecto p " +
                    "LEFT JOIN proyecto_rol pr " +
                    "ON p.id_proyecto = pr.id_proyecto "+
                    "WHERE p.id_categoria_proyecto IN (:listaCategorias) AND p.id_estado_proyecto IN (:listaEstados) "+
                    "AND pr.id_rol IN (:listaRolId) ",
            nativeQuery = true)
    Page<Proyecto> filtroProyecto(@Param("listaCategorias") List<Long> listaCategorias, @Param("listaEstados") List<Long> listaEstados , @Param("listaRolId") List<Long> listaRolId ,Pageable pagina);

    Page<Proyecto> findByIdResponsable(Persona idResponsable,Pageable pagina);



}
