package com.grupocinco.kilosapi;

import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainDePruebas {
    @Autowired
    private DestinatarioRepository repoDestinatario;
    @Autowired
    CajaRepository repoCaja;
    @Autowired

    @PostConstruct
    public void datos(){
        Destinatario des1 = Destinatario.builder()
                                        .nombre("Comedor Pagés del Corro")
                                        .direccion("C/ Pagés del Corro 34")
                                        .personaContacto("María")
                                        .telefono("954347087")
                                        .build();
        Destinatario des2 = Destinatario.builder()
                                        .nombre("Hermanitas de los Pobres")
                                        .direccion("C/ Luis Montoto 43")
                                        .personaContacto("José")
                                        .telefono("954543092")
                                        .build();
    }

}
