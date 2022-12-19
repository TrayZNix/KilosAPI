package com.grupocinco.kilosapi.dto.caja;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.tiene.TieneDto;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CajaDto {
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class, CajaViews.CajasList.class})
    private Long id;
    @JsonView({CajaViews.CajasList.class})
    private String qr;
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class, CajaViews.CajasList.class})
    private Integer numeroCaja;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private Double totalKilos;
    private Destinatario destinatario;
    @JsonView({CajaViews.CajasList.class, DestinatarioViews.DestinatarioConcretoDetalles.class})
    private List<TieneDto> contenido;

    public static CajaDto of(Caja c){
        return CajaDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numeroCaja(c.getNumeroCaja())
                .destinatario(c.getDestinatario())
                .totalKilos(c.getKilosTotales())
                .build();

    }
//    public static Caja to(CajaDto c){
//        return Caja.builder()
//                .id(c.getId())
//                .qr(c.getQr())
//                .numeroCaja(c.getNumeroCaja())
//                .destinatario(c.getDestinatario())
//                .totalKilos(c.getTotalKilos())
//                .build();
//
//    }
}
