package com.grupocinco.kilosapi.dto.destinatario;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.tiene.LineaCajaContenidoDto;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DestinatarioCajaActualizadaDto {
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private Long id;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private String nombreDestinatario;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private CajaContenidoDto caja;
}
