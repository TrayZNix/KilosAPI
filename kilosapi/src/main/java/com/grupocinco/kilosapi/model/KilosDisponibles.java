package com.grupocinco.kilosapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KilosDisponibles {

    @Id
    @ManyToOne
    @JoinColumn(name="tipoAlimento", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    private TipoAlimento tipoAlimento;
    private Double cantidadDisponible;

}
