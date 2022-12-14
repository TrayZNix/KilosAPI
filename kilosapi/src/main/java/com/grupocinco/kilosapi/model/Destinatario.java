package com.grupocinco.kilosapi.model;


import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;

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
    private Long id;

    private String nombre, direccion, personaContacto, telefono;
    
    /*
    @ToString.Exclude
    @OneToMany(mappedBy = "destinatario", fetch = FetchType.EAGER)
    @Builder.Default
    private Lista<Caja> cajas = new ArrayList<>();


    @PreRemove
    public void setNullDestinatario() {
        cajas.forEach(a -> a.setDestinatario(null));
    }
    */



}
