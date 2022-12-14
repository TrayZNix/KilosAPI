package com.grupocinco.kilosapi.dto.clase;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClaseDetalleDto {
    private Long id;

    private String nombre, tutor;

    private int numAportaciones;

    private double numKilos;
}
