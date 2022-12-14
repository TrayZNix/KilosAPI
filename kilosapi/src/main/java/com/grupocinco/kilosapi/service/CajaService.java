package com.grupocinco.kilosapi.service;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Tiene getAlimentoEnCaja(TipoAlimento t, Caja c){
        return repoCaja.getTipoAlimentoEnCaja(t,c);
    }
    public Caja actualizarDatosCajas(Caja c){
        c.setTotalKilos(repoTiene.getPesoTotalCaja(c));
        System.out.println(c.getTotalKilos());
        return repository.save(c);
    }

    public Double getKilosTotales(Caja c) {
        return repoTiene.getPesoTotalCaja(c);
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

    public Optional<Caja> findById(Long id) {
        return repoCaja.findById(id);
    }
    public Caja add(Caja c) {
        return repoCaja.save(c);
    }
    public boolean existById(Long id){
        return repoCaja.existsById(id);
    }
    public void deleteById(Long id) {
        repoCaja.deleteById(id);
    }


}
