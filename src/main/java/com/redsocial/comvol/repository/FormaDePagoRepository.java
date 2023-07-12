package com.redsocial.comvol.repository;

import com.redsocial.comvol.model.FormaDePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago,Long> {


}
