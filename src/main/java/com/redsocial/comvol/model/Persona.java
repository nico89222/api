package com.redsocial.comvol.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@SqlResultSetMapping(name = "Persona")
@Table(name = "Persona")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    @Column(nullable = false)
    private String nombre;
    private String apellido;
    private String email;
    private String acercaDe;
    private String urlImagenPersona;
    private String contrasena;
    private Integer tipoUsuario;
    private LocalDate fechaAltaPersona;
    private LocalDate fechaBajaPersona;
    private boolean suscripcion;
    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;
    private String provincia;
    private String localidad;
    private String perfilExterno;


    @ManyToMany
    @JoinTable(name = "persona_rol",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_persona","id_rol"}))
    private List<Rol> listaRoles;

    @OneToMany
    List<PersonaProyecto> listaProyectos;

}
