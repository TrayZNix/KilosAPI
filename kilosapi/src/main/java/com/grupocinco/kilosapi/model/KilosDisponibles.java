package com.grupocinco.kilosapi.model;

import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import com.grupocinco.kilosapi.service.BaseServiceImpl;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KilosDisponibles implements Serializable {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId()
    private TipoAlimento tipoAlimento;
    private Double cantidadDisponible;

    public void addToTipoAlimento(TipoAlimento a) {
        this.tipoAlimento = a;
        a.setKilosDisponible(this);
    }

    public void removeFromTipoAlimento(TipoAlimento a) {
        this.tipoAlimento = null;
        a.setKilosDisponible(null);
    }

    public void addCantidad(Double c) {
        this.cantidadDisponible += c;

        if (cantidadDisponible < 0.0)
            this.cantidadDisponible = 0.0;

    }
}


