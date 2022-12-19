package com.grupocinco.kilosapi.dto.destinatario;

import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DestinatarioMapper {
    @Autowired
    private CajaMapper mapperCaja;

    @Autowired
    private CajaRepository repoCaja;

    public DestinatarioDto toDestinatarioDto(Destinatario d){
        List<Caja> cajas = repoCaja.getRelacionesCajasByDestinatario(d);
        List<CajaDto> cajasDto = new ArrayList<CajaDto>();
        cajas.forEach(caja -> {
            cajasDto.add(mapperCaja.toCajaDto(caja));
        });
        return DestinatarioDto.builder()
                .id(d.getId())
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .cajas(cajasDto).build();
    }
}
