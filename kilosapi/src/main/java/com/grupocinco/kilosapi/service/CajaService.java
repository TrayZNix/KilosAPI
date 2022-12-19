package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CajaService {
    private final CajaRepository cajaRepository;
    @Autowired
    private CajaRepository repoCaja;

    @Autowired
    private TieneRepository repoTiene;

    public Caja save(Caja caja) {
        return cajaRepository.save(caja);
    }

    public List<Caja> actualizarDatosCajas(List<Caja> c) {
        //TODO Hago esto como consulta? Lo dejo asi?
        c.forEach(caja -> {
            caja.setTotalKilos(repoTiene.getPesoTotalCaja(caja));
        });
        return repoCaja.saveAll(c);
    }

    public Optional<Caja> getCajaByIdAndIdTipo(Long id, Long idTipo) {
        return repoCaja.getCajaByIdAndIdTipo(id, idTipo);
    }
}
