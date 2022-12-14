package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.*;

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
    private Double totalKilos;
    @ManyToOne
    @JoinColumn(name = "DESTINATARIO", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    private Destinatario destinatario;

    public void addDestinatario(Destinatario d){
        this.destinatario = d;
        d.getCajas().add(this);
    }

    public void removeDestinatario(Destinatario d){
        this.destinatario = null;
        d.getCajas().remove(this);
    }

}
