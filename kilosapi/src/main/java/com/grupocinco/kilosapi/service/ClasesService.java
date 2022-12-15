package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.repository.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasesService {
    private ClaseRepository repository;

    public List<Clase> findAll() {
        return repository.findAll();
    }
}
