package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Embeddable
public class TipoAlimento implements  Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "tipoAlimento", cascade = CascadeType.ALL)
    @JoinColumn(name="tipoAlimento_id", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    private KilosDisponibles kilosDisponible;

    public void addToKilosDisponibles(KilosDisponibles k){
        this.kilosDisponible = k;
        k.setTipoAlimento(this);
    }
}
