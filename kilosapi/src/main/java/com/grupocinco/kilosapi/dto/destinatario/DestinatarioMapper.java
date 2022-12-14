package com.grupocinco.kilosapi.dto.destinatario;

import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.service.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DestinatarioMapper {
    @Autowired
    private CajaMapper mapperCaja;
    @Autowired
    private CajaService servCaja;

    public DestinatarioDto toDestinatarioDto(Destinatario d){
        List<Caja> cajas = d.getCajas();
        List<CajaContenidoDto> cajasDto = mapperCaja.toCajaContenidoDto(cajas);
        return DestinatarioDto.builder()
                .id(d.getId())
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .cajas(cajasDto).build();
    }

    public DestinatarioCajaActualizadaDto toDestinatarioCajaActualizadaDto(Destinatario d, Caja c){
        c = servCaja.actualizarDatosCajas(c);
        return DestinatarioCajaActualizadaDto
                .builder()
                .id(d.getId())
                .nombreDestinatario(d.getNombre())
                .caja(mapperCaja.toCajaContenidoDto(c))
                .build();
    }
}
