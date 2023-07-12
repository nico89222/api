package com.redsocial.comvol.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Forma_Pago")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormaDePago {

    @Id
    private Long idFormaDePago;
    private String descripcionFormaDePago;


}
