package com.grupocinco.kilosapi.dto.kilosDisponibles;

import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.tiene.LineaCajaContenidoDto;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KilosMapper {
    public KilosDispUnAlimentoDto toKilosDispUnAliDto(KilosDisponibles kl, DetalleAportacion detAportacion){

        return KilosDispUnAlimentoDto.builder()
                .idAportación(detAportacion.getAportacion().getId())
                .cantidadDisponible(kl.getCantidadDisponible())
                .detalleAportacion(detAportacion)
                .build();

    }

}
