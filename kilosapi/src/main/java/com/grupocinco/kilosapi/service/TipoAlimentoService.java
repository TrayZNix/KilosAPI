package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoAlimentoService {
    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimento save(TipoAlimento tipoAlimento) {
        return tipoAlimentoRepository.save(tipoAlimento);
    }
}
