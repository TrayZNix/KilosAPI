package com.grupocinco.kilosapi.dto.aportacion;

import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.TipoAlimento;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleAportacionDto {

    private Long idTipoAlimento;
    private Double cantidadKilos;

    public static DetalleAportacionDto of(DetalleAportacion da){
        return  DetalleAportacionDto.builder()
                .cantidadKilos(da.getCantidad_en_kgs())
                .idTipoAlimento(da.getTipoAlimento().getId())
                .build();
    }
}
