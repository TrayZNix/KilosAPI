package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Caja c SET c.destinatario = null WHERE c.destinatario = :id")
    public void deleteRelacionesCajasDestinatarioBorrado(@Param("id") Destinatario id);

    @Query("SELECT c FROM Caja c WHERE c.destinatario = :id")
    public List<Caja> getRelacionesCajasByDestinatario(@Param("id") Destinatario id);
}
