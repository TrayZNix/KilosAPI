package com.grupocinco.kilosapi.model;

import lombok.*;

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
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_TIPOALIMENTO"))
    private TipoAlimento tipoAlimento;

    private Double cantidad_en_kgs;

    @ManyToOne
    @JoinColumn(name = "aportacion_id", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_APORTACION"))
    private Aportacion aportacion;

    @PreRemove
    public void restarKilos() {
        KilosDisponibles kilos = tipoAlimento.getKilosDisponible();
        kilos.setCantidadDisponible(kilos.getCantidadDisponible() - cantidad_en_kgs);
    }

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleAportacionId implements Serializable {
        private Long idAportacion;
        @GeneratedValue
        private Long numLinea;
    }
}
