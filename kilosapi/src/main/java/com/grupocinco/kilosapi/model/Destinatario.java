package com.grupocinco.kilosapi.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.view.DestinatarioViews;
import lombok.*;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Destinatario {


    @Id
    @GeneratedValue
    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.DestinatarioList.class})
    private Long id;

    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.ModeloPostDestinatario.class})
    private String nombre, direccion, personaContacto, telefono;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "destinatario", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Caja> cajas = new ArrayList<Caja>();

    @JsonView({DestinatarioViews.DestinatarioList.class, DestinatarioViews.DestinatarioConcreto.class})
    private Double totalKilos;

    @JsonView(DestinatarioViews.DestinatarioList.class)
    private int[] numerosCaja;

    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    private Integer cantidadCajas;



    @PreRemove
    public void setNullDestinatario() {
        cajas.forEach(a -> a.setDestinatario(null));
        cajas = null;
    }




}
