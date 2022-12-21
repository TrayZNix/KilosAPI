package com.grupocinco.kilosapi.dto.caja;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CajaDeleteDto {

    private Long id;

    private String name;

    private Double cantKilos;

}
