package com.grupocinco.kilosapi.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class TienePK implements Serializable {
    long cajaId;
    long tipoAlimentoId;

}
