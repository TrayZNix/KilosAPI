package com.grupocinco.kilosapi.model;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class DetalleAportacion {
    @EmbeddedId
    private DetalleAportacionId detalleAportacionId;
    @ManyToOne
    @JoinColumn(name = "id")
    private TipoAlimento tipoAlimento;

    private Double cantidad_en_kgs;

    @PreRemove //TODO comprobar que se guarda la cantidad restada
    public void restarKilos() {
        KilosDisponibles kilos = tipoAlimento.getKilosDisponible();
        kilos.setCantidadDisponible(kilos.getCantidadDisponible() - cantidad_en_kgs);
    }

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @Builder
    public static class DetalleAportacionId implements Serializable {
        @ManyToOne()
        private Aportacion aportacion;


        private Integer numLinea;
    }
}
