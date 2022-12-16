package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @ToString
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@Builder
public class Aportacion {
    @Id @GeneratedValue
    private Long id;

    private String fecha;

   @ManyToOne
   @JoinColumn(name="clase_id", foreignKey = @ForeignKey(name = "FK_APORTACION_CLASE"))
   private Clase clase;

   @ToString.Exclude
   @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @Builder.Default
   private List<DetalleAportacion> detalles = new ArrayList<>();


//   public void addToClase(Clase c) {
//       this.clase = c;
//       c.getAportacion().add(this);
//   }
//
//   public void removeFromClase(Clase c) {
//       this.clase = null;
//       c.getAportacion().remove(this);
//   }
}
