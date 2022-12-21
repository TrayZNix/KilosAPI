package com.grupocinco.kilosapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.ClaseViews;
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
public class Clase {


    @Id
    @GeneratedValue
    @JsonView(ClaseViews.NewClase.class)
    private Long idClase;

    @JsonView(ClaseViews.NewClase.class)
    private String nombre, tutor;


    @ToString.Exclude
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Aportacion> aportaciones = new ArrayList<>();


    @PreRemove
    public void setNullClase() {
        aportaciones.forEach(a -> a.setClase(null));
    }

}
