package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;

import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Clase;

import com.grupocinco.kilosapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AportacionService {
    private final AportacionRepository aportacionRepository;

    private final ClaseService claseService;

    public Optional<ClaseInfoAportacionDto> aportacionDetalleByClaseId(Long id) {
        Optional<Clase> clase = claseService.findById(id);
        if (clase.isPresent()){
            return Optional.of(ClaseInfoAportacionDto.of(clase.get()));
        } else
            return Optional.empty();
    }

    public Aportacion save(Aportacion aportacion) {
        return aportacionRepository.save(aportacion);
    }

    public boolean existsById(Long id) {
        return aportacionRepository.existsById(id);
    }

    public void deleteById(Long id) {
        aportacionRepository.deleteById(id);
    }

    public List<Aportacion> findAll() {
        return aportacionRepository.findAll();
    }
    public Optional<Aportacion> findById(Long id) {
        return aportacionRepository.findById(id);
    }
    public Aportacion add(Aportacion a) {
        return aportacionRepository.save(a);
    }
}
