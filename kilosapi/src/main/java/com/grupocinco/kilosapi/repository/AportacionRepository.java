package com.grupocinco.kilosapi.repository;

import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
    @Query(value = """
            select c
            from Clase c
            where c.idClase = :claseId
            """)
    public Optional<Clase> aportacionDetalleByClaseId(@Param("claseId") Long id);

    @Modifying
    @Transactional
    @Query("delete from DetalleAportacion a where a.tipoAlimento = :id")
    public void deleteTipoAlimento(@Param("id") TipoAlimento id);
}
