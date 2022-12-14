package com.grupocinco.kilosapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Clase {


    @Id
    @GeneratedValue
    private Long id;

    private String nombre, tutor;

    /*
    @ToString.Exclude
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Aportacion> aportaciones = new ArrayList<>();


    @PreRemove
    public void setNullClase() {
        aportaciones.forEach(a -> a.setClase(null));
    }
    */


}
