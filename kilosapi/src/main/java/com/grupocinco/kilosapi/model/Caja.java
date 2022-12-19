package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Caja {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "QR")
    private String qr;
    @Column(name = "NUMERO_CAJA")
    private Integer numeroCaja;
    @Column(name = "TOTAL_KILOS")
    private Double kilosTotales;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "DESTINATARIO", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    private Destinatario destinatario;

    @OneToMany(mappedBy = "caja", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    private List<Tiene> lineas = new ArrayList<Tiene>();
    public void addDestinatario(Destinatario d){
        this.destinatario = d;
        d.getCajas().add(this);
    }

    public void removeDestinatario(Destinatario d){
        this.destinatario.getCajas().remove(this);
        this.destinatario = null;
        d.getCajas().remove(this);
    }

    @PreRemove
    public void removeCaja(){

    }
}
