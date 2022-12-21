package com.grupocinco.kilosapi.dto.detalleAportacion;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleAportacionInfoDto {
    private String nombreTipoAlimento;

    private Double cantidadKg;

    public static DetalleAportacionInfoDto of(DetalleAportacion detalleAportacion) {
        return DetalleAportacionInfoDto.builder().nombreTipoAlimento(detalleAportacion.getTipoAlimento().getNombre()).cantidadKg(detalleAportacion.getCantidad_en_kgs()).build();
    }
}
