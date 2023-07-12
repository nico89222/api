package com.redsocial.comvol.model;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Pais")
@Data
public class Pais {

    @Id
    private Long idPais;
    private String descripcionPais;


}
