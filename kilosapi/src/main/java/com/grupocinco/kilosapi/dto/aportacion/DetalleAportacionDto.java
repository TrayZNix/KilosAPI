package com.grupocinco.kilosapi.dto.aportacion;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleAportacionDto {

    private Long claseId;
    private Map<Long, String> tipoAlimentoCantidad;

}
