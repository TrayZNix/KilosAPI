package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.KilosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KilosDisponiblesRepository extends JpaRepository<TipoAlimento, KilosDisponibles> {
}
