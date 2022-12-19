package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
    @Query(value = """
            select c
            from Clase c
            where c.id = :claseId
            """)
    public Optional<Clase> aportacionDetalleByClaseId(@Param("claseId") Long id); //TODO ver que esto funcione
}
