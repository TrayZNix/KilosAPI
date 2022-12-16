package com.grupocinco.kilosapi.dto.caja;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;

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
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class})
    private Double totalKilos;
    @JsonView({CajaViews.CajasList.class})
    private Destinatario destinatario;
    private String destinatarioString;
}
