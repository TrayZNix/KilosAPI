package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    @Query(value = """
            select new com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto(
            c.idClase,
            c.nombre,
            c.tutor,
            size(c.aportaciones),
                (select coalesce(sum(d.cantidad_en_kgs), 0.0)
                from DetalleAportacion d
                where d.detalleAportacionId.idAportacion in (
                    select a.id from Clase c join c.aportaciones a where c.idClase = :id
                )))
            from Clase c
            where c.idClase = :id
            """)
    public Optional<ClaseDetalleDto> claseDetalleById(@Param("id") Long id);

    //            cast(  as double))
}
