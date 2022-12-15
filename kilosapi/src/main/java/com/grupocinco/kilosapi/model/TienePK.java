package com.grupocinco.kilosapi.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@Embeddable
public class TienePK implements Serializable {
    long cajaId;
    long tipoAlimentoId;

}
