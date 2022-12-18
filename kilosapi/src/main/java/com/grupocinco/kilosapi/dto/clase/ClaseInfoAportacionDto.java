package com.grupocinco.kilosapi.dto.clase;

import com.grupocinco.kilosapi.dto.aportacion.AportacionInfoDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClaseInfoAportacionDto {
    private Long id;

    private List<AportacionInfoDto> aportaciones;
}
