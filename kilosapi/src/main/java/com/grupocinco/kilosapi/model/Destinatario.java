package com.grupocinco.kilosapi.model;


import lombok.*;
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
    private Long id;

    private String nombre, direccion, personaContacto, telefono;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "destinatario", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Caja> cajas = new ArrayList<Caja>();

    /*

    @PreRemove
    public void setNullDestinatario() {
        cajas.forEach(a -> a.setDestinatario(null));
    }
    */



}
