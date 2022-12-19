package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class CajaService extends BaseServiceImpl<Caja, Long, CajaRepository>{
    @Autowired
    private CajaRepository repoCaja;
    @Autowired
    private TieneRepository repoTiene;
    public List<Caja> actualizarDatosCajas(List<Caja> c){
        c.forEach(caja -> {
            caja.setTotalKilos(repoTiene.getPesoTotalCaja(caja));
        });
        return repository.saveAll(c);
    }
    public Caja actualizarDatosCajas(Caja c){
            c.setTotalKilos(repoTiene.getPesoTotalCaja(c));
        return repository.save(c);
    }

        public void deleteRelacionesCajasDestinatarioBorrado (Destinatario d){
            repoCaja.deleteRelacionesCajasDestinatarioBorrado(d);
        }

    public Optional<Caja> getCajaByIdAndIdTipo(Long id, Long idTipo) {
        return repoCaja.getCajaByIdAndIdTipo(id, idTipo);
    }

    public void asignarCaja(Long c, Destinatario d){
        repository.asignarCaja(c, d);
    }
}
