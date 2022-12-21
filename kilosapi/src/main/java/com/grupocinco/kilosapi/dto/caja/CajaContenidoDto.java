package com.grupocinco.kilosapi.dto.caja;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.tiene.LineaCajaContenidoDto;
import com.grupocinco.kilosapi.dto.tiene.TieneDto;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Destinatario;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CajaContenidoDto {
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private Long id;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetallesConQr.class)
    private String qr;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private Integer numeroCaja;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private Double totalKilos;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private List<LineaCajaContenidoDto> contenido;
}
