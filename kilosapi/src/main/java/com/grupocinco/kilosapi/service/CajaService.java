package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {
    private final CajaRepository cajaRepository;

    public Optional<Caja> getCajaByIdAndIdTipo(Long id, Long idTipo) {
        return cajaRepository.getCajaByIdAndIdTipo(id, idTipo);
    }
}
