package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CajaService {
    @Autowired
    private CajaRepository repoCaja;

    @Autowired
    private TieneRepository repoTiene;


    public Caja actualizarDatosCajaById(Caja c){
    //TODO Hago esto como consulta? Lo dejo asi?
            c.setTotalKilos(repoTiene.getPesoTotalCaja(c));
            return repoCaja.save(c);
    }
}
