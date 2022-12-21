package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, Long> {


    @Query("SELECT d FROM DetalleAportacion d WHERE d.tipoAlimento = :id")
    public List<DetalleAportacion> findDetalleAportacionByTipoAlimentoId(@Param("id") TipoAlimento id);


    @Query("SELECT k.cantidadDisponible from KilosDisponibles k WHERE tipoAlimento = :id")
    public Double getKilosByTipoRelacionado(@Param("id") TipoAlimento id);

    @Transactional
    @Modifying
    @Query("UPDATE KilosDisponibles k SET k.cantidadDisponible = k.cantidadDisponible + :cantidad WHERE tipoAlimento = :id")
    public void setKilosDisponiblesToTipoRelacionado(@Param("id") TipoAlimento id, @Param("cantidad") Double cantidad);


    @Query("SELECT k.cantidadDisponible FROM KilosDisponibles k WHERE k.tipoAlimento = :ali")
    public Double getCantidadKilosByDetalleAportacion(@Param("ali") TipoAlimento ali);

}
