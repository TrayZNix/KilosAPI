package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TieneService extends BaseServiceImpl<Tiene, TienePK, TieneRepository>{

    public Tiene saveLinea(Tiene t){
        t.setId(TienePK.builder()
                .tipoAlimentoId(t.getTipoAlimento()
                        .getId())
                .cajaId(t.getCaja()
                        .getId())
                .build());
        return repository.save(t);
    }
    public void saveListaLineas(List<Tiene> lista){
        for(Tiene t: lista){
            t.setId(TienePK.builder()
                    .tipoAlimentoId(t.getTipoAlimento()
                            .getId())
                    .cajaId(t.getCaja()
                            .getId())
                    .build());
            repository.save(t);
        }
    }
    public List<Tiene> getLineasCajas(Caja c){
        return repository.getLineasCajas(c);
    }

    public Optional<Double> findIfAlreadySavedTipoAlimentoInCaja(TipoAlimento t, Caja c){
        return repository.findIfAlreadySavedTipoAlimentoInCaja(t, c);
    }
}
