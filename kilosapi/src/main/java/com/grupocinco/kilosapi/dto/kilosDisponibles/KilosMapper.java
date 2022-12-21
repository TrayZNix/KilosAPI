package com.grupocinco.kilosapi.dto.kilosDisponibles;

import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.tiene.LineaCajaContenidoDto;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.Tiene;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KilosMapper {
    public KilosDisponiblesDto toKilosDispDto(KilosDisponibles kl, List<Aportacion> lsp){
        return KilosDisponiblesDto.of(kl).setListAportacionDto(lsp);
    }

}
