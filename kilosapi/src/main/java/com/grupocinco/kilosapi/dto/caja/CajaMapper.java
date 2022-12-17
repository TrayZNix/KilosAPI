package com.grupocinco.kilosapi.dto.caja;

import com.grupocinco.kilosapi.model.Caja;
import org.springframework.stereotype.Component;

@Component
public class CajaMapper {
    public CajaDto toCajaDto(Caja c){
        return CajaDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numeroCaja(c.getNumeroCaja())
                .destinatario(c.getDestinatario())
                .totalKilos(c.getTotalKilos())
                .contenido(c.getLineas())
                .build();
    }
}
