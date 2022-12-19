package com.grupocinco.kilosapi.dto.tiene;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@JsonView(DestinatarioViews.DestinatarioCajaActualizadaDtoJson.class)
public class LineaCajaContenidoDto {
    private Long id;
    private String nombre;
    private Double cantidad;

    public static LineaCajaContenidoDto of(Tiene t){
        return LineaCajaContenidoDto.builder()
                                        .id(t.getId().getTipoAlimentoId())
                                        .nombre(t.getTipoAlimento().getNombre())
                                        .cantidad(t.getCantidadKgs())
                                        .build();
    }
}
