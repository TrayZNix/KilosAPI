package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString
@Builder
public class Aportacion {
    @Id @GeneratedValue
    private Long id;

    private String fecha;

//    @ManyToOne
//    @JoinColumn(name="clase_id", foreignKey = @ForeignKey(name = "FK_APORTACION_CLASE"))
//    private Clase clase;
//
//    @ToString.Exclude
//    @OneToMany(mappedBy = "aportacion", fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<DetalleAportacion> detalles = new ArrayList<>();
//
//
//
//    @PreRemove
//    public void setNullDetalles(){
//        detalles.forEach(a ->a.setAportacion(null));
//    }
//    public void addToClase(Clase c) {
//        this.clase = c;
//        c.getAportacion().add(this);
//    }
//
//    public void removeFromClase(Clase c) {
//        this.clase = null;
//        c.getAportacion().remove(this);
//    }
}
