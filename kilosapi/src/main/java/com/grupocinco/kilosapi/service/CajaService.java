package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajaService {

    @Autowired
    public CajaRepository cajaRepository;

    public Tiene getAlimentoEnCaja(TipoAlimento t, Caja c){
        return cajaRepository.getTipoAlimentoEnCaja(t,c);
    }

}
