package com.grupocinco.kilosapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tiene {
    @EmbeddedId
    private TienePK id;

    @ManyToOne
    @MapsId("cajaId")
    @JoinColumn(name = "CAJA_ID")
    private Caja caja;

    @ManyToOne
    @MapsId("tipoAlimentoId")
    @JoinColumn(name = "TIPO_ALIMENTO_ID")
    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private TipoAlimento tipoAlimento;

    @Column(name = "CANTIDAD_KILOS")
    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private Double cantidadKgs;

}
