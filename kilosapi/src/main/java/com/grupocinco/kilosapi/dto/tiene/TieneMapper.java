package com.grupocinco.kilosapi.dto.tiene;

import com.grupocinco.kilosapi.model.Tiene;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TieneMapper {
    public List<TieneDto> ofList(List<Tiene> lista){
        List<TieneDto> listaDto = new ArrayList<TieneDto>();
        for (Tiene t : lista){
            listaDto.add(TieneDto.of(t));
        }
        return listaDto;
    }

    public List<LineaCajaContenidoDto> fromListtoLineaCajaCont(List<Tiene> lista){
        List<LineaCajaContenidoDto> listaDto = new ArrayList<LineaCajaContenidoDto>();
        lista.forEach(linea -> {
            listaDto.add(LineaCajaContenidoDto.of(linea));
        });
        return listaDto;
    }
}
