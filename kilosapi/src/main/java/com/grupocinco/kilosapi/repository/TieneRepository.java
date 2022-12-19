package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TieneRepository extends JpaRepository<Tiene, TienePK> {
    @Query("SELECT t FROM Tiene t WHERE t.caja = :id")
    public List<Tiene> getLineasCajas(@Param("id") Caja id);

    @Query("SELECT SUM(t.cantidadKgs) FROM Tiene t WHERE t.caja = :id")
    public Double getPesoTotalCaja(@Param("id") Caja id);

    @Modifying
    @Transactional
    @Query("delete from Tiene t where t.tipoAlimento = :id")
    public void deleteTipoAlimento(@Param("id")TipoAlimento id);
}
