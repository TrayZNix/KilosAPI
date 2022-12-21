package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.dto.ranking.RankingDto;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.repository.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClaseService {
    private final ClaseRepository repository;

    public List<Clase> findAll() {
        return repository.findAll();
    }

    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Clase save(Clase clase) {
        return repository.save(clase);
    }

    public Optional<ClaseDetalleDto> claseDetalleById(Long id) {
        return repository.claseDetalleById(id);
    }

    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    public Clase edit(Clase clase){
        return repository.save(clase);
    }

    public List<RankingDto> getRankingClase(){
        return repository.getRankingClase();
    }
    public Double getAvgKilos(Long id){
        return repository.getAvgKilos(id);
    }

    public Double getSumKilos(Long id){
        return repository.getSumKilos(id);
    }
}
