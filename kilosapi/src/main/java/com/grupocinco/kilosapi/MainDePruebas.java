package com.grupocinco.kilosapi;

import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.DestinatarioService;
import com.grupocinco.kilosapi.service.TieneService;
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
    private TipoAlimentoRepository repoTipoAlimento;
    @Autowired
    private TieneRepository repoTiene;
    @Autowired
    private DestinatarioService destServ;
    @Autowired
    private TieneService tieneService;

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


        TipoAlimento t1 = TipoAlimento.builder().nombre("Arroz").build();
        TipoAlimento t2 = TipoAlimento.builder().nombre("Azúcar").build();
        TipoAlimento t3 = TipoAlimento.builder().nombre("Leche").build();
        TipoAlimento t4 = TipoAlimento.builder().nombre("Huevo").build();
        TipoAlimento t5 = TipoAlimento.builder().nombre("Zanahoria").build();



        repoDestinatario.saveAll(List.of(des1, des2));
        repoCaja.saveAll(List.of(c1, c2, c3));
        repoTipoAlimento.saveAll(List.of(t1, t2, t3, t4, t5));


        Tiene tiene1 = Tiene.builder()
                .caja(c1)
                .tipoAlimento(t1)
                .cantidadKgs(2.5)
                .build();
        Tiene tiene2 = Tiene.builder()
                .caja(c1)
                .tipoAlimento(t2)
                .cantidadKgs(2.6)
                .build();
        Tiene tiene3 = Tiene.builder()
                .caja(c2)
                .tipoAlimento(t2)
                .cantidadKgs(6.3)
                .build();
        Tiene tiene4 = Tiene.builder()
                .caja(c3)
                .tipoAlimento(t4)
                .cantidadKgs(2.6)
                .build();
        Tiene tiene5 = Tiene.builder()
                .caja(c3)
                .tipoAlimento(t5)
                .cantidadKgs(2.4)
                .build();


        tieneService.saveListaLineas(List.of(tiene1, tiene2, tiene3, tiene4, tiene5));

    }

}
