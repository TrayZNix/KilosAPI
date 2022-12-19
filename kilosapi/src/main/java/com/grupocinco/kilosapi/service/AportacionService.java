package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AportacionService {
    private final AportacionRepository aportacionRepository;

    public Optional<ClaseInfoAportacionDto> aportacionDetalleByClaseId(Long id) {
        return aportacionRepository.aportacionDetalleByClaseId(id);
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
}
