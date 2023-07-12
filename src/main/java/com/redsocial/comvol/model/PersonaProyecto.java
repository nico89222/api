package com.redsocial.comvol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Persona_Proyecto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaProyecto;

    @ManyToOne
    private Persona persona;
    @ManyToOne
    private Proyecto proyecto;
    @ManyToOne
    private Rol rol;
    private LocalDate fechaAltaPersonaAlProyecto;
    private LocalDate fechaBajaPersonaAlProyecto;

}
