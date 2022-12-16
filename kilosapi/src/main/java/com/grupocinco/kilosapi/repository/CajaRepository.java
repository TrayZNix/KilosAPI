package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Caja c SET c.destinatario = null WHERE c.destinatario = :id")
    public void deleteRelacionesCajasDestinatarioBorrado(@Param("id") Destinatario id);

    @Modifying
    @Transactional
    @Query("UPDATE TipoAlimento t SET c.")
    public void deleteListadoTiposAlimentosEnCajas(@Param("id") TipoAlimento id);
}
