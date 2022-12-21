
package com.grupocinco.kilosapi.dto.detalleAportacion;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import lombok.*;

@JsonView({AportacionViews.AportacionById.class})
public record DetalleAportacionDto(Long numLinea, String nombreAlimento, Double cantidadKgs) { }