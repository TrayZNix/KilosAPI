package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.KilosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;

//Hay que cambiarlo por TipoAlimento en vez de Long
public interface KilosDisponiblesRepository extends JpaRepository<Long, KilosDisponibles> {
}