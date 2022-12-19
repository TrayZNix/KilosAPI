package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CajaService extends BaseServiceImpl<Caja, Long, CajaRepository>{
    @Autowired
    private TieneRepository repoTiene;
    public List<Caja> actualizarDatosCajas(List<Caja> c){
        //TODO Hago esto como consulta? Lo dejo asi?
        c.forEach(caja -> {
            caja.setTotalKilos(repoTiene.getPesoTotalCaja(caja));
        });
        return repository.saveAll(c);
    }

    public void deleteRelacionesCajasDestinatarioBorrado(Destinatario d){
        repository.deleteRelacionesCajasDestinatarioBorrado(d);
    }
}
