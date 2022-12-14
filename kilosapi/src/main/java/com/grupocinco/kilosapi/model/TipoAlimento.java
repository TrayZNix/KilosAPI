package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "tipoAlimento", cascade = CascadeType.ALL)
    private KilosDisponible kilosDisponible = new KilosDisponibles();
}
