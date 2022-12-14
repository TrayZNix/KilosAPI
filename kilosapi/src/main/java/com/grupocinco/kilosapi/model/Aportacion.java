package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Aportacion {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "clase_id", foreignKey = @ForeignKey(name = "FK_APORTACION_CLASE"))
    private Clase clase;
    @ToString.Exclude
    @OneToMany(mappedBy = "aportacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleAportacion> detalles = new ArrayList<DetalleAportacion>();
    public void addToClase(Clase c) {
        this.clase = c;
        c.getAportaciones().add(this);
    }
    public void removeFromClase(Clase c) {
        this.clase = null;
        c.getAportaciones().remove(this);
    }

    public void addDetalleAportacion(DetalleAportacion d) {

        if(detalles.size()==0){
            d.getDetalleAportacionId().setNumLinea(1L);
        }else{
            d.getDetalleAportacionId().setNumLinea(
                    detalles.get(detalles.size()-1).getDetalleAportacionId().getNumLinea() +1L);
        }

        this.getDetalles().add(d);
        d.setAportacion(this);
    }

    public void removeDetalleAportacion(DetalleAportacion d) {
        this.getDetalles().remove(d);
        d.setAportacion(null);
    }
}
