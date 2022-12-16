package com.grupocinco.kilosapi.dto.destinatario;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.view.CajaViews;
import com.grupocinco.kilosapi.view.DestinatarioViews;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DestinatarioDto {
    @JsonView({DestinatarioViews.DestinatarioConcreto.class,
            DestinatarioViews.DestinatarioList.class,
            CajaViews.CajasList.class,
            DestinatarioViews.ModeloPostDestinatario.class})
    private Long id;
    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.ModeloPostDestinatario.class})
    private String direccion;
    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.ModeloPostDestinatario.class})
    private String nombre;
    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.ModeloPostDestinatario.class})
    private String personaContacto;
    @JsonView({DestinatarioViews.DestinatarioConcreto.class, DestinatarioViews.ModeloPostDestinatario.class})
    private String telefono;
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    private List<Caja> cajas;
    @JsonView({DestinatarioViews.DestinatarioList.class, DestinatarioViews.DestinatarioConcreto.class})
    private Double totalKilos;
    @JsonView(DestinatarioViews.DestinatarioList.class)
    private int[] numerosCaja;
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    private Integer cantidadCajas;

    public static DestinatarioDto of(Destinatario d){
        return DestinatarioDto.builder()
                .id(d.getId())
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .cajas(d.getCajas()).build();
    }
    public static Destinatario to(DestinatarioDto d){
        return Destinatario.builder()
                .direccion(d.getDireccion())
                .nombre(d.getNombre())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .cajas(d.getCajas()).build();
    }


}
