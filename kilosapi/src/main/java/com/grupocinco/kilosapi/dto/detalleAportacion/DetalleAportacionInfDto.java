
package com.grupocinco.kilosapi.dto.detalleAportacion;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.AportacionViews;

@JsonView({AportacionViews.AportacionById.class})
public record DetalleAportacionInfDto(Long numLinea, String nombreAlimento, Double cantidadKgs) { }