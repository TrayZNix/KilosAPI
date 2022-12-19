package com.grupocinco.kilosapi.dto.aportacion;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor@NoArgsConstructor
@Getter @Setter @ToString
public class AportacionDto {
    @JsonView({AportacionViews.ListaAportacion.class,AportacionViews.AportacionById.class} )
    private Long id;
    @JsonView({AportacionViews.ListaAportacion.class,AportacionViews.AportacionById.class})
    private LocalDate fecha;
    @JsonView({AportacionViews.ListaAportacion.class,AportacionViews.AportacionById.class})
    private String nombreClase;
    @JsonView({AportacionViews.ListaAportacion.class,AportacionViews.AportacionById.class})
    private Double kilosTotales;

    @JsonView({AportacionViews.AportacionById.class})
    List<DetalleAportacion> detalleAportaciones;


    public static AportacionDto of(Aportacion a){
        double kgTotales = 0.0;

        for (DetalleAportacion det: a.getDetalles()) kgTotales += det.getCantidad_en_kgs();

        return AportacionDto.builder()
                .id(a.getId())
                .fecha(a.getFecha())
                .nombreClase(a.getClase().getNombre())
                .kilosTotales(kgTotales)
                .build();
    }
}
