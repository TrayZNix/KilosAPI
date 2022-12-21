package com.grupocinco.kilosapi.dto.caja;

import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDetalleCaja;
import com.grupocinco.kilosapi.dto.tipoAlimento.TipoAlimentoDto;
import com.grupocinco.kilosapi.model.Caja;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CajaDetalleDto {
    private Long id;

    private String qr;

    private Integer numeroCaja;

    private Double kilosTotales;

    private DestinatarioDetalleCaja destinatario;

    private List<TipoAlimentoDto> tiposAlimento;

    public static CajaDetalleDto of(Caja caja) {
        return CajaDetalleDto.builder()
                .id(caja.getId())
                .qr(caja.getQr())
                .numeroCaja(caja.getNumeroCaja())
                .kilosTotales(caja.getTotalKilos())
                .destinatario(DestinatarioDetalleCaja.builder()
                        .id(caja.getDestinatario().getId())
                        .nombre(caja.getDestinatario().getNombre()).build())
                .tiposAlimento(caja.getLineas().stream().map(tipo -> new TipoAlimentoDto(tipo.getTipoAlimento().getId(), tipo.getTipoAlimento().getNombre(), tipo.getCantidadKgs())).toList())
                .build();
    }
}
