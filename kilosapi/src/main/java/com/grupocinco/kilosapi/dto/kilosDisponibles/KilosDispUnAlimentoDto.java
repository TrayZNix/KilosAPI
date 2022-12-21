package com.grupocinco.kilosapi.dto.kilosDisponibles;

import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class KilosDispUnAlimentoDto {

    private Long idAportación;

    private DetalleAportacion detalleAportacion;

    private Double cantidadDisponible;
}
