package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleAportacionRepository extends JpaRepository<Integer, Aportacion, DetalleAportacion> {
}
