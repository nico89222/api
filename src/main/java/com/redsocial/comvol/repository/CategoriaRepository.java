package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {


}
