package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DestinatarioService {
    @Autowired
    private DestinatarioRepository repo;

    @Autowired
    private DestinatarioMapper mapperDest;

    public DestinatarioDto calculosDestinatario(Destinatario d){
        DestinatarioDto dto = mapperDest.toDestinatarioDto(d);
        List<CajaDto> cajas = dto.getCajas();
        List<Integer> numeros = new ArrayList<Integer>();
        double total = 0;
        for(CajaDto caja: cajas){
            total = total + caja.getTotalKilos();
            numeros.add(caja.getNumeroCaja());
        }
        int[] arr = numeros.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
        dto.setNumerosCaja(arr);
        dto.setTotalKilos(total);
        dto.setCantidadCajas(arr.length); //Actualizar numeros de cajas
        return dto;
    }
}
