package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CajaService {
    @Autowired
    private TieneRepository repoTiene;
    @Autowired
    private CajaRepository repoCaja;

    public List<Caja> actualizarDatosCajas(List<Caja> c){
        c.forEach(caja -> {
            caja.setKilosTotales(repoTiene.getPesoTotalCaja(caja));
        });
        return repoCaja.saveAll(c);
    }
}
