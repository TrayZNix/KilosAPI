package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.repository.DetalleAportacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetalleAportacionService {
    @Autowired
    private DetalleAportacionRepository repo;
    public DetalleAportacion add(DetalleAportacion t) {
        return repo.save(t);
    }

    public void delete(DetalleAportacion detalleAportacion) {
        repo.delete(detalleAportacion);
    }

    public Optional<DetalleAportacion> findDetalleAportacionByAportacionIdAndLinea(Long id, Long linea) {
        return repo.findDetalleAportacionByAportacionIdAndLinea(id, linea);
    }
}
