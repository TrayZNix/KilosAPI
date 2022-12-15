package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class TipoAlimento {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "tipoAlimento", cascade = CascadeType.ALL)
//    private List<KilosDisponibles> kilosDisponible = new ArrayList<>();
}
