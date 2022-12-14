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
public class DetalleAportacion {
    @Id
    private Aportacion aportacion;

    @Id
    private Integer numLinea;

    @ManyToOne()
    private TipoAlimento tipoAlimento;

    private Double cantidad_en_kgs;

    @PreRemove //TODO comprobar que se guarda la cantidad restada
    public void restarKilos() {
        KilosDisponibles kilos = tipoAlimento.getKilosDisponible();
        kilos.setCantidadDisponible(kilos.getCantidadDisponible - cantidad_en_kgs);
    }
}
