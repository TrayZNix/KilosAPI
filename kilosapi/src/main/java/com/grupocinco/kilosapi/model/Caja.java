package com.grupocinco.kilosapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.view.CajaViews;
import com.grupocinco.kilosapi.view.DestinatarioViews;
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
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class, CajaViews.CajasList.class})
    @Id
    @GeneratedValue
    private Long id;
    @JsonView({CajaViews.CajasList.class})
    @Column(name = "QR")
    private String qr;
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class, CajaViews.CajasList.class})
    @Column(name = "NUMERO_CAJA")
    private Integer numeroCaja;
    @JsonView({DestinatarioViews.DestinatarioConcretoDetalles.class})
    @Column(name = "TOTAL_KILOS")
    private Double totalKilos;
    @ManyToOne
    @JoinColumn(name = "DESTINATARIO", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    @JsonView({CajaViews.CajasList.class})
    private Destinatario destinatario;
    @Transient
    private String destinatarioString;



    public void addDestinatario(Destinatario d){
        this.destinatario = d;
        d.getCajas().add(this);
    }

    public void removeDestinatario(Destinatario d){
        this.destinatario = null;
        d.getCajas().remove(this);
    }

}
