package com.grupocinco.kilosapi.dto.clase;

import com.grupocinco.kilosapi.model.Clase;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClaseDto {
    private Long id;

    private String nombre, tutor;

    public ClaseDto of(Clase clase) {
        return ClaseDto.builder().id(clase.getId()).nombre(clase.getNombre()).tutor(clase.getTutor()).build();
    }
}
