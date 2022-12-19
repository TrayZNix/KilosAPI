package com.grupocinco.kilosapi.service;


import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {


    public final KilosDisponiblesRepository kilosDisponiblesRepository;

    public List<DetalleAportacion> findDetalleAportacionByTipoAlimentoId(TipoAlimento t){
        return kilosDisponiblesRepository.findDetalleAportacionByTipoAlimentoId(t);
    };





}
