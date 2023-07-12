package com.redsocial.comvol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Postulacion_Persona")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostulacionPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPostulacionPersona;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona idPersona;

    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto idProyecto;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol idRol;

    @ManyToOne
    @JoinColumn(name = "id_estado_persona")
    private EstadoPersona idEstadoPersona;



}
