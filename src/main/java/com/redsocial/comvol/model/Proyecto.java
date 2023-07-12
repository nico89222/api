package com.redsocial.comvol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Proyecto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;

    @ManyToOne
    @JoinColumn(name = "id_responsable")
    private Persona idResponsable;

    @ManyToOne
    @JoinColumn(name = "id_categoria_proyecto")
    private Categoria categoriaProyecto;

    @ManyToOne
    @JoinColumn(name = "id_estado_proyecto")
    private Estado estadoProyecto;

    private String tituloProyecto;
    private String descripcionProyecto;
    private int limitePersonasProyecto;
    private String urlImagenProyecto;
    private LocalDate fechaAltaProyecto;
    private LocalDate fechaBajaProyecto;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago")
    private FormaDePago formaDePago;

    private boolean esEmpresa;

    private String razonSocial;


}
