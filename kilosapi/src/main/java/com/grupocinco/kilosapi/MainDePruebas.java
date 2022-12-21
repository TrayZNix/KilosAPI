package com.grupocinco.kilosapi;

import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class MainDePruebas {
    @Autowired
    private DestinatarioRepository repoDestinatario;
    @Autowired
    private CajaRepository repoCaja;
    @Autowired
    private TipoAlimentoRepository repoTipoAlimento;
    @Autowired
    private TieneService tieneService;

    @Autowired
    private DetalleAportacionService detalleAportacionService;
    @Autowired
    private AportacionService aportacionService;
    @Autowired
    private ClaseService claseService;

    @Autowired
    private CajaService cajaService;

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
                .build();
        Caja c2 = Caja.builder()
                .qr("tetete")
                .numeroCaja(2)
                .build();
        Caja c3 = Caja.builder()
                .qr("rwrwrww")
                .numeroCaja(3)
                .build();
        repoCaja.saveAll(List.of(c1, c2, c3));
        c1.addDestinatario(des1);
        c2.addDestinatario(des1);
        c3.addDestinatario(des2);

        KilosDisponibles k1 = KilosDisponibles.builder().cantidadDisponible(20.3).build();
        KilosDisponibles k2 = KilosDisponibles.builder().cantidadDisponible(10.0).build();
        KilosDisponibles k3 = KilosDisponibles.builder().cantidadDisponible(12.5).build();
        KilosDisponibles k4 = KilosDisponibles.builder().cantidadDisponible(26.0).build();
        KilosDisponibles k5 = KilosDisponibles.builder().cantidadDisponible(29.99).build();

        TipoAlimento t1 = TipoAlimento.builder().nombre("Arroz").build();
        TipoAlimento t2 = TipoAlimento.builder().nombre("Azúcar").build();
        TipoAlimento t3 = TipoAlimento.builder().nombre("Leche").build();
        TipoAlimento t4 = TipoAlimento.builder().nombre("Huevo").build();
        TipoAlimento t5 = TipoAlimento.builder().nombre("Zanahoria").build();

        KilosDisponibles k6 = KilosDisponibles.builder().tipoAlimento(t1).cantidadDisponible(10.0).build();
        KilosDisponibles k7 = KilosDisponibles.builder().tipoAlimento(t2).cantidadDisponible(10.0).build();
/*        t1.setKilosDisponible(k6);
        t2.setKilosDisponible(k7);*/


        t1.addToKilosDisponibles(k6);
        t2.addToKilosDisponibles(k7);
        t3.addToKilosDisponibles(k3);
        t4.addToKilosDisponibles(k4);
        t5.addToKilosDisponibles(k5);

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

        Clase cl1 = Clase.builder()
                .tutor("Luismi")
                .nombre("Clase tal")
                .build();
        Clase cl2 = Clase.builder()
                .tutor("Miguel")
                .nombre("Clase tal 2")
                .build();

        claseService.save(cl1);
        claseService.save(cl2);

        Aportacion a1 = Aportacion.builder()
                .fecha(LocalDate.now())
                .clase(cl1)
                .build();

        Aportacion a2 = Aportacion.builder()
                .fecha(LocalDate.now())
                .clase(cl2)
                .build();

        Aportacion a3 = Aportacion.builder()
                .fecha(LocalDate.now())
                .clase(cl1)
                .build();

        aportacionService.add(a1);
        aportacionService.add(a2);
        aportacionService.add(a3);

        DetalleAportacion dt1 = DetalleAportacion.builder()
                .detalleAportacionId(DetalleAportacion.DetalleAportacionId.builder().idAportacion(a1.getId()).numLinea(1L).build())
                .tipoAlimento(t1)
                .cantidad_en_kgs(15.0)
                .aportacion(a1)
                .build();

        DetalleAportacion dt2 = DetalleAportacion.builder()
                .detalleAportacionId(DetalleAportacion.DetalleAportacionId.builder().idAportacion(a1.getId()).numLinea(2L).build())
                .tipoAlimento(t2)
                .cantidad_en_kgs(10.0)
                .aportacion(a1)
                .build();

        DetalleAportacion dt3 = DetalleAportacion.builder()
                .detalleAportacionId(DetalleAportacion.DetalleAportacionId.builder().idAportacion(a3.getId()).numLinea(3L).build())
                .tipoAlimento(t3)
                .cantidad_en_kgs(10.0)
                .aportacion(aportacionService.findById(a3.getId()).get())
                .build();


        a1.addDetalleAportacion(dt1);
        a1.addDetalleAportacion(dt2);
        a3.addDetalleAportacion(dt3);

        a1.removeDetalleAportacion(dt2);

        a1.addDetalleAportacion(dt2);

        detalleAportacionService.add(dt1);
        detalleAportacionService.add(dt2);
        detalleAportacionService.add(dt3);

        claseService.save(cl1);
        claseService.save(cl2);

        aportacionService.add(a1);
        aportacionService.add(a2);

        cajaService.actualizarDatosCajas(List.of(c1, c2, c3));
        cajaService.saveAll(List.of(c1, c2, c3));
    }
}
