package com.grupocinco.kilosapi.model;

import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class KilosDisponibles {

    @Id
    @ManyToOne
    @JoinColumn(name="tipoAlimento_id", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    //Hay que cambiarlo por TipoAlimento en vez de Long
    private Long tipoAlimento;
    private Double cantidadDisponible;

}
