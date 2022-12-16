package com.grupocinco.kilosapi.dtos;


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
