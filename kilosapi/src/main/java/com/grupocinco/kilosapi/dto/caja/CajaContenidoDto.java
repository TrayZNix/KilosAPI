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
@JsonView(DestinatarioViews.DestinatarioCajaActualizadaDtoJson.class)
public class CajaContenidoDto {
    private Long id;
    private String qr;
    private Integer numeroCaja;
    private Double totalKilos;
    private Destinatario destinatario;
    private List<LineaCajaContenidoDto> contenido;
}
