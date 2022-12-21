package com.grupocinco.kilosapi.dto.tiene;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TieneDto {
    private CajaDto caja;

    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private TipoAlimento tipoAlimento;

    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private Double cantidadKgs;

    public static TieneDto of(Tiene t){
        return TieneDto.builder()
                .caja(CajaDto.of(t.getCaja()))
                .tipoAlimento(t.getTipoAlimento())
                .cantidadKgs(t.getCantidadKgs())
                .build();
    }
}
