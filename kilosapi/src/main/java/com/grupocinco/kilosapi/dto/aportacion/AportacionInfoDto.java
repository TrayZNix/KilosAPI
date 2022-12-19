package com.grupocinco.kilosapi.dto.aportacion;

import com.grupocinco.kilosapi.dto.detalleAportacion.DetalleAportacionInfoDto;
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
}
