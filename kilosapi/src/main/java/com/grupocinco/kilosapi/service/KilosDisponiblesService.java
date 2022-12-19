package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KilosDisponiblesService extends BaseServiceImpl<KilosDisponibles, Long, KilosDisponiblesRepository>{
    @Autowired
    private KilosDisponiblesRepository repo;

    public KilosDisponibles add(KilosDisponibles t) {
        return repo.save(t);
    }

    public Double getKilosByTipoRelacionado(TipoAlimento t){
        return repository.getKilosByTipoRelacionado(t);
    }

    public void setKilosDisponiblesToTipoRelacionado(TipoAlimento t, Double d){
        repository.setKilosDisponiblesToTipoRelacionado(t, d);
    }
}
