package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoAlimentoService {
    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimento save(TipoAlimento tipoAlimento) {
        return tipoAlimentoRepository.save(tipoAlimento);
    }

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

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
