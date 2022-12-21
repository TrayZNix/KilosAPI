package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Caja c SET c.destinatario = null WHERE c.destinatario = :id")
    public void deleteRelacionesCajasDestinatarioBorrado(@Param("id") Destinatario id);


    @Query("SELECT t FROM Tiene t WHERE t.tipoAlimento = :id AND t.caja = :idCaja")
    public Tiene getTipoAlimentoEnCaja(@Param("id") TipoAlimento id, @Param("idCaja") Caja idCaja);

    @Modifying
    @Transactional
    @Query("UPDATE Caja c SET c.destinatario = :destinatario WHERE c.id = :id")
    public void asignarCaja(@Param("id") Long id, @Param("destinatario") Destinatario destinatario);

    @Query("SELECT c FROM Caja c WHERE c.destinatario = :id")
    public List<Caja> getRelacionesCajasByDestinatario(@Param("id") Destinatario id);

    @Query(value = """
            select c
            from Caja c join fetch c.lineas t
            where
            c.id = :id and
            :idTipo = any (select t1.tipoAlimento.id from Tiene t1 where t1.caja.id = :id)
            """)
    public Optional<Caja> getCajaByIdAndIdTipo(@Param("id") Long id, @Param("idTipo") Long idTipo);
}
