package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.AportacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AportacionService {
    @Autowired
    private AportacionRepository repo;

    public List<Aportacion> findAll() {
        return repo.findAll();
    }
    public Optional<Aportacion> findById(Long id) {
        return repo.findById(id);
    }
}
