package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.repository.DetalleAportacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleAportacionService {
    @Autowired
    private DetalleAportacionRepository repo;
    public DetalleAportacion add(DetalleAportacion t) {
        return repo.save(t);
    }
}
