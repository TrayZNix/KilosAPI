package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DetalleAportacionRepository extends JpaRepository<DetalleAportacion, Long> {
    @Query("""
            select d
            from DetalleAportacion d
            where d.detalleAportacionId.numLinea = :linea and d.aportacion.id = :id
            """)
    public Optional<DetalleAportacion> findDetalleAportacionByAportacionIdAndLinea(@Param("id") Long id, @Param("linea") Long linea);
}
