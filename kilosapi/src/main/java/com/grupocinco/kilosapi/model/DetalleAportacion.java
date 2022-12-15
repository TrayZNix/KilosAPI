package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @ManyToOne()
    private TipoAlimento tipoAlimento;

    private Double cantidad_en_kgs;

//    @PreRemove //TODO comprobar que se guarda la cantidad restada
//    public void restarKilos() {
//        List<KilosDisponibles> kilos = tipoAlimento.getKilosDisponible();
//        kilos.forEach(k -> k.setCantidadDisponible(k.getCantidadDisponible() - cantidad_en_kgs));
//    }

    @Embeddable
    public static class DetalleAportacionId implements Serializable {
        @ManyToOne()
        private Aportacion aportacion;

        private Integer numLinea;
    }
}
