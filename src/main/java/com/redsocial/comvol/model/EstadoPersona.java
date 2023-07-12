package com.redsocial.comvol.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Estado_Persona")
@Data
public class EstadoPersona {

    @Id
    private Long idEstadoPersona;
    private String descripcionEstadoPersona;

}
