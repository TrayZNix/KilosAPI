package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class KilosDisponiblesService extends BaseServiceImpl<KilosDisponibles, Long, KilosDisponiblesRepository>{
    @Autowired
    private KilosDisponiblesRepository repo;

    public KilosDisponibles add(KilosDisponibles t) {
        return repo.save(t);
    }
    public List<DetalleAportacion> findDetalleAportacionByTipoAlimentoId(TipoAlimento t){
        return repo.findDetalleAportacionByTipoAlimentoId(t);
    }
    public Double getKilosByTipoRelacionado(TipoAlimento t){
        return repository.getKilosByTipoRelacionado(t);
    }

    public void setKilosDisponiblesToTipoRelacionado(TipoAlimento t, Double d){
        repository.setKilosDisponiblesToTipoRelacionado(t, d);
    }
}
