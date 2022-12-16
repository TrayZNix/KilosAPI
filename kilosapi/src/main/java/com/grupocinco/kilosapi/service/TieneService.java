package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TieneService {
    @Autowired
    private TieneRepository tieneRepo;
    public void saveLinea(Tiene t){
        t.setId(TienePK.builder()
                .tipoAlimentoId(t.getTipoAlimento()
                        .getId())
                .cajaId(t.getCaja()
                        .getId())
                .build());
        tieneRepo.save(t);
    }
    public void saveListaLineas(List<Tiene> lista){
        for(Tiene t: lista){
            t.setId(TienePK.builder()
                    .tipoAlimentoId(t.getTipoAlimento()
                            .getId())
                    .cajaId(t.getCaja()
                            .getId())
                    .build());
            tieneRepo.save(t);
        }
    }
}
