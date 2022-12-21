package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoAlimentoService {
    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimento save(TipoAlimento tipoAlimento) {
        return tipoAlimentoRepository.save(tipoAlimento);
    }


    public List<TipoAlimento> findAll() {
        return tipoAlimentoRepository.findAll();
    }

    public Optional<TipoAlimento> findById(Long id) {
        return tipoAlimentoRepository.findById(id);
    }

    public void delete(TipoAlimento alimento) {
        tipoAlimentoRepository.delete(alimento);
    }

    public void deleteById(Long id) {
        tipoAlimentoRepository.deleteById(id);
    }

    public TipoAlimento add(TipoAlimento t) {
        return tipoAlimentoRepository.save(t);
    }

    public boolean existsById(Long id) {
        return tipoAlimentoRepository.existsById(id);
    }
}
