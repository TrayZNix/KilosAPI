package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity @ToString
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class KilosDisponibles implements Serializable {
    @Id
    @ManyToOne @JoinColumn(name="tipoAlimento_id", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    private TipoAlimento  tipoAlimento;
    private Double cantidadDisponible;

    public void addToTipoAlimento(TipoAlimento a){
        this.tipoAlimento = a;
        a.getKilosDisponible().add(this);
    }
    public void removeFromTipoAlimento(TipoAlimento a){
        this.tipoAlimento = null;
        a.getKilosDisponible().remove(this);
    }
}
