package com.grupocinco.kilosapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_TIPOALIMENTO"))
    private TipoAlimento tipoAlimento;

    private Double cantidad_en_kgs;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "aportacion_id", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_APORTACION")) //TODO Esto hay que cogerlo con pizas, porque hay que preguntar a luismi
    private Aportacion aportacion;

    @PreRemove //TODO comprobar que se guarda la cantidad restada
    public void restarKilos() {
        KilosDisponibles kilos = tipoAlimento.getKilosDisponible();
        kilos.setCantidadDisponible(kilos.getCantidadDisponible() - cantidad_en_kgs);
    }

/*    @PreRemove //TODO esta es la unica manera que he encontrado de que se elimine de alguna manera
    public void eliminarDeAportacion() {
        this.aportacion.getDetalles().remove(this);
    }*/

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleAportacionId implements Serializable {
        private Long idAportacion;


        private Long numLinea;
    }
}
