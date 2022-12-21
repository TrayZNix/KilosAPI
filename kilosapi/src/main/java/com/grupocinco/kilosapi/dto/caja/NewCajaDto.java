package com.grupocinco.kilosapi.dto.caja;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NewCajaDto {

    private String qr;

    private Integer numeroCaja;

}
