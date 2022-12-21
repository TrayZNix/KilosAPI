package com.grupocinco.kilosapi.dto.ranking;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RankingDto {

    private Integer posicion; //1º, 2º, o 3º;
    private String nombreClase;
    private Integer cantidadAportaciones;
    private Double mediaKilosAportacion;
    private Double kilosTotalesAportados;

    /* Lista con las clases y las aportaciones totales que han hecho.
    Debe aparecer la posición en el ranking,
     el nombre de la clase,
      la cantidad de aportaciones,
       la media de kilos por aportación
        y los kilos totales aportados. */
}
