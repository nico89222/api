package com.redsocial.comvol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rol")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    private Long idRol;
    private String descripcionRol;

}
