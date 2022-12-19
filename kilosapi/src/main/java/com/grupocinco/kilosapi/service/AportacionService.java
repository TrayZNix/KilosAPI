package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.AportacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AportacionService {
    private final AportacionRepository aportacionRepository;
    @Autowired
    private AportacionRepository repo;

    public Optional<ClaseInfoAportacionDto> aportacionDetalleByClaseId(Long id) {
        Optional<Clase> clase = aportacionRepository.aportacionDetalleByClaseId(id);
        if (clase.isPresent()){
            return Optional.of(ClaseInfoAportacionDto.of(clase.get()));
        } else
            return Optional.of(null);
    }

    public boolean existsById(Long id) {
        return aportacionRepository.existsById(id);
    }

    public void deleteById(Long id) {
        aportacionRepository.deleteById(id);
    }

    public List<Aportacion> findAll() {
        return repo.findAll();
    }
    public Optional<Aportacion> findById(Long id) {
        return repo.findById(id);
    }
    public Aportacion add(Aportacion a) {
        return repo.save(a);
    }
}
