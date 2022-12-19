package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajaService {
    @Autowired
    private TieneRepository repoTiene;
    @Autowired
    private CajaRepository repoCaja;
    public List<Caja> actualizarDatosCajas(List<Caja> c){
        //TODO Hago esto como consulta? Lo dejo asi?
        c.forEach(caja -> {
            caja.setTotalKilos(repoTiene.getPesoTotalCaja(caja));
        });
        return repoCaja.saveAll(c);
    }

    public Optional<Caja> findById(Long id) {
        return repoCaja.findById(id);
    }
    public Caja add(Caja c) {
        return repoCaja.save(c);
    }
    public boolean existById(Long id){
        return repoCaja.existsById(id);
    }
    public void deleteById(Long id) {
        repoCaja.deleteById(id);
    }
}
