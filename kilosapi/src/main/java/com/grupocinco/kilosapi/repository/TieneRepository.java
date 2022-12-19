package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TieneRepository extends JpaRepository<Tiene, TienePK> {
    @Query("SELECT t FROM Tiene t WHERE t.caja = :id")
    public List<Tiene> getLineasCajas(@Param("id") Caja id);

    @Query("SELECT SUM(t.cantidadKgs) FROM Tiene t WHERE t.caja = :id")
    public Double getPesoTotalCaja(@Param("id") Caja id);

    @Query("SELECT COALESCE(t.cantidadKgs, 0) FROM Tiene t WHERE t.caja = :idCaja AND t.tipoAlimento = :idTipo")
    public Optional<Double> findIfAlreadySavedTipoAlimentoInCaja(@Param("idTipo")TipoAlimento tipo, @Param("idCaja") Caja caja);
}
