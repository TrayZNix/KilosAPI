package com.grupocinco.kilosapi.model;

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
    private TipoAlimento tipoAlimento;

    @Column(name = "CANTIDAD_KILOS")
    private Double cantidadKgs;

}
