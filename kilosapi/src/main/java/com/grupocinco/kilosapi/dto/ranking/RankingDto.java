package com.grupocinco.kilosapi.dto.ranking;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RankingDto implements Comparable<RankingDto>{

    private String posicion; //1º, 2º, o 3º;
    private Long id;
    private String nombre;
    private Integer cantidadAportaciones;
    private Double mediaKilosAportados;
    private Double kilosTotalesAportados;

    @Override
    public int compareTo(RankingDto o) {
        if(this.kilosTotalesAportados > o.getKilosTotalesAportados()) return -1;
        else if (this.kilosTotalesAportados < o.getKilosTotalesAportados()) return 1;
        else{
            if (this.cantidadAportaciones > o.getCantidadAportaciones()) return -1;
            else if (this.cantidadAportaciones < o.getCantidadAportaciones()) return 1;
            else{
                if (this.mediaKilosAportados > o.getMediaKilosAportados()) return -1;
                else if (this.mediaKilosAportados > o.getMediaKilosAportados()) return 1;
                else return 0;
            }
        }
    }
}
