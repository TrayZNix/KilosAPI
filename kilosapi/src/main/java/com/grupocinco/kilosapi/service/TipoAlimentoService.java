package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAlimentoService {

    @Autowired
    private TipoAlimentoRepository repo;

    public List<TipoAlimento> findAll() {
        return repo.findAll();
    }
    public Optional<TipoAlimento> findById(Long id) {
        return repo.findById(id);
    }
    public void delete(TipoAlimento alimento) {
        repo.delete(alimento);
    }
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    public TipoAlimento add(TipoAlimento t) {
        return repo.save(t);
    }

    public boolean existsById(Long id){
       return repo.existsById(id);
    }
}
