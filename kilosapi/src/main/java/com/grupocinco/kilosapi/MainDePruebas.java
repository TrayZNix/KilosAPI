package com.grupocinco.kilosapi;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.service.DestinatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDePruebas {
    @Autowired
    private DestinatarioRepository repoDestinatario;
    @Autowired
    private CajaRepository repoCaja;

    @Autowired
    private DestinatarioService destServ;

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

        Caja c1 = Caja.builder()
                .qr("qrqrqr")
                .numeroCaja(1)
                .totalKilos(37.67)
                .build();
        Caja c2 = Caja.builder()
                .qr("tetete")
                .numeroCaja(2)
                .totalKilos(13.26)
                .build();
        Caja c3 = Caja.builder()
                .qr("rwrwrww")
                .numeroCaja(3)
                .totalKilos(17.57)
                .build();
        c1.addDestinatario(des1);
        c2.addDestinatario(des1);
        c3.addDestinatario(des2);
        repoDestinatario.saveAll(List.of(des1, des2));
        repoCaja.saveAll(List.of(c1, c2, c3));

        repoDestinatario.saveAll(List.of(destServ.calculosDestinatario(des1), destServ.calculosDestinatario(des2)));


    }

}
