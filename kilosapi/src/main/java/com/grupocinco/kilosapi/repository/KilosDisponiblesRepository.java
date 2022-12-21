
//package com.grupocinco.kilosapi.repository;
//
//import com.grupocinco.kilosapi.model.KilosDisponibles;
//import org.springframework.data.jpa.repository.JpaRepository;
//
////Hay que cambiarlo por TipoAlimento en vez de Long
//public interface KilosDisponiblesRepository extends JpaRepository<Long, KilosDisponibles> {
//}

package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

//Hay que cambiarlo por TipoAlimento en vez de Long
public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, Long> {

    @Query("SELECT k.cantidadDisponible from KilosDisponibles k WHERE tipoAlimento = :id")
    public Double getKilosByTipoRelacionado(@Param("id") TipoAlimento id);

    @Transactional
    //UPDATE Caja c SET c.destinatario = null WHERE c.destinatario = :id
    @Modifying
    @Query("UPDATE KilosDisponibles k SET k.cantidadDisponible = k.cantidadDisponible + :cantidad WHERE tipoAlimento = :id")
    public void setKilosDisponiblesToTipoRelacionado(@Param("id") TipoAlimento id, @Param("cantidad") Double cantidad);
}
