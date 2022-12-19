package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Hay que cambiarlo por TipoAlimento en vez de Long
public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, Long> {


    @Modifying
    @Transactional
    @Query("SELECT d FROM DetalleAportacion d WHERE d.tipoAlimento = :id")
    public List<DetalleAportacion> findDetalleAportacionByTipoAlimentoId(@Param("id") TipoAlimento id);

}
