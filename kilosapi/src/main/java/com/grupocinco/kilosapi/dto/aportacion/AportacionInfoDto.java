package com.grupocinco.kilosapi.dto.aportacion;

import com.grupocinco.kilosapi.dto.detalleAportacion.DetalleAportacionInfoDto;
import com.grupocinco.kilosapi.model.Aportacion;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AportacionInfoDto {

    private LocalDate fecha;

    private List<DetalleAportacionInfoDto> detalleAportaciones;

    public static AportacionInfoDto of(Aportacion aportacion) {
        return AportacionInfoDto.builder().fecha(aportacion.getFecha()).detalleAportaciones(aportacion.getDetalles().stream().map(DetalleAportacionInfoDto::of).toList()).build();
    }
}
