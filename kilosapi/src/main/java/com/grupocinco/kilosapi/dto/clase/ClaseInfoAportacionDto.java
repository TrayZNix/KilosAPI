package com.grupocinco.kilosapi.dto.clase;

import com.grupocinco.kilosapi.dto.aportacion.AportacionInfoDto;
import com.grupocinco.kilosapi.model.Clase;
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

    public static ClaseInfoAportacionDto of(Clase clase) {
        return ClaseInfoAportacionDto.builder().id(clase.getId()).aportaciones(clase.getAportaciones().stream().map(AportacionInfoDto::of).toList()).build();
    }
}
