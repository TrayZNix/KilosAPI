package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
/*    @Query(value = """
            select new com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto(
                :claseId,
                new com.grupocinco.kilosapi.dto.aportacion.AportacionInfoDto(select a.fecha,
                    new com.grupocinco.kilosapi.dto.detalleAportacion.DetalleAportacionInfoDto(
                        select d.tipoAlimento.nombre as tipoAlimento, d.cantidad_en_kgs as cantidad 
                        from DetalleAportacion d 
                        where d.detalleAportacionId.aportacion.clase.id = :claseId)
                from Aportacion a where a.clase.id = :claseId))
            """)*/
    public Optional<ClaseInfoAportacionDto> aportacionDetalleByClaseId(@Param("claseId") Long id); //TODO ver que esto funcione
}
