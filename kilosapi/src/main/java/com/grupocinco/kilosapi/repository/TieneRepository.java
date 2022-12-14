package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TieneRepository extends JpaRepository<Tiene, TienePK> {
}
