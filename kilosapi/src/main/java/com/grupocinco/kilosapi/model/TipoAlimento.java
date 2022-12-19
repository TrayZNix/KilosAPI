package com.grupocinco.kilosapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private Long id;
    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private String nombre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "tipoAlimento", cascade = CascadeType.ALL)
    @JoinColumn(name="tipoAlimento_id", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
//    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private KilosDisponibles kilosDisponible;

    public void addToKilosDisponibles(KilosDisponibles k){
        this.kilosDisponible = k;
        k.setTipoAlimento(this);
    }
}
