package com.redsocial.comvol.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "Estado")
@Data
public class Estado {

    @Id
    private Long idEstado;
    private String descripcionEstado;



}
