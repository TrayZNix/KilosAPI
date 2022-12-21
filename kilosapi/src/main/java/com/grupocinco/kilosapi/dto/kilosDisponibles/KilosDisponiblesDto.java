package com.grupocinco.kilosapi.dto.kilosDisponibles;

import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class KilosDisponiblesDto {

    private Long idTipoAlimento;

    private String nombre;

    private Double cantidadDisponible;


    public static KilosDisponiblesDto of(KilosDisponibles k){
        return KilosDisponiblesDto.builder()
                .idTipoAlimento(k.getTipoAlimento().getId())
                .nombre(k.getTipoAlimento().getNombre())
                .cantidadDisponible(k.getCantidadDisponible())
                .build();
    }

}
