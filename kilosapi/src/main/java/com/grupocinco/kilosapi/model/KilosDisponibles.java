package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity @ToString
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class KilosDisponibles implements Serializable{
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private TipoAlimento tipoAlimento;
    private Double cantidadDisponible;

    public void addToTipoAlimento(TipoAlimento a){
        this.tipoAlimento = a;
        a.setKilosDisponible(this);
    }
    public void removeFromTipoAlimento(TipoAlimento a){
        this.tipoAlimento = null;
        a.setKilosDisponible(null);
    }

}


