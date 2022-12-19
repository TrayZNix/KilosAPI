package com.grupocinco.kilosapi.dto.caja;

import com.grupocinco.kilosapi.dto.tiene.LineaCajaContenidoDto;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CajaMapper {
    @Autowired
    private TieneMapper mapperTiene;

    public CajaDto toCajaDto(Caja c){
        return CajaDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numeroCaja(c.getNumeroCaja())
                .destinatario(c.getDestinatario())
                .totalKilos(c.getTotalKilos())
                .contenido(mapperTiene.ofList(c.getLineas()))
                .build();
    }
    public List<CajaDto> toListCajaDto(List<Caja> lista) {
        List<CajaDto> listaDto = new ArrayList<CajaDto>();
        for (Caja c : lista) {
            listaDto.add(CajaDto.builder()
                    .id(c.getId())
                    .contenido(mapperTiene.ofList(c.getLineas()))
                    .numeroCaja(c.getNumeroCaja())
                    .qr(c.getQr())
                    .destinatario(c.getDestinatario())
                    .totalKilos(c.getTotalKilos())
                    .build());
        }
        return listaDto;
    }

    public CajaContenidoDto toCajaContenidoDto(Caja c) {
        List<Tiene> lineas = c.getLineas();
        List<LineaCajaContenidoDto> lineasDto = new ArrayList<LineaCajaContenidoDto>();
        lineas.forEach(linea -> lineasDto.add(LineaCajaContenidoDto.of(linea)));
        return CajaContenidoDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numeroCaja(c.getNumeroCaja())
                .totalKilos(c.getTotalKilos())
                .contenido(lineasDto)
                .build();
    }

}
