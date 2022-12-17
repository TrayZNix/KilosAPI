package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KilosDisponiblesService {
    @Autowired
    private KilosDisponiblesRepository repo;

    public KilosDisponibles add(KilosDisponibles t) {
        return repo.save(t);
    }

}
