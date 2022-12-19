package com.grupocinco.kilosapi.dto.destinatario;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DestinatarioDto {
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    private Long id;
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    private String direccion;
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    private String nombre;
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    private String personaContacto;
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    private String telefono;
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    private Double totalKilos;
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    private Integer cantidadCajas;
    @JsonView(DestinatarioViews.DestinatarioList.class)
    private int[] numerosCaja;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private List<CajaContenidoDto> cajas;

    public static DestinatarioDto of(Destinatario d){
        return DestinatarioDto.builder()
                .id(d.getId())
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .build();
    }
    public static Destinatario to(DestinatarioDto d){
        return Destinatario.builder()
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono()).build();
    }


}
