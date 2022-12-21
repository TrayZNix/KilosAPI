package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.dto.caja.CajaContenidoDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TienePK;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinatarioService extends BaseServiceImpl<Destinatario, Long, DestinatarioRepository>{

    @Autowired
    private DestinatarioMapper mapperDest;

    @Autowired
    private CajaService servCaja;

    @Autowired
    private CajaMapper mapperCaja;

    /**
     * Sirve para devolver un DTO de destinatario con los datos
     * de información necesarios correctamente actualizados
     *
     * @param d Objeto destinatario
     * @return Objeto DestinatarioDto
     */
    public DestinatarioDto setDatosDestinatarioDto(Destinatario d){
        //Declaracion de variables
        DestinatarioDto dto = mapperDest.toDestinatarioDto(d);
        List<Integer> numeros = new ArrayList<Integer>();

        //Actualizamos los pesos de las cajas relacionadas con el destinatario
        List<CajaContenidoDto> cajasDto = mapperCaja.toCajaContenidoDto(servCaja.actualizarDatosCajas(d.getCajas()));
        dto.setCajas(cajasDto);

        //Calculamos los kilos totales enviados al destinatario determinado
        double total = 0;
        for(CajaContenidoDto caja: cajasDto){
            total = total + caja.getTotalKilos();
            numeros.add(caja.getNumeroCaja());
        }
        dto.setTotalKilos(total);

        //Creado de array de enteros con los numeros de las cajas asignadas
        int[] arr = numeros.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
        dto.setNumerosCaja(arr);

        //Conteo de cajas enviadas
        dto.setCantidadCajas(arr.length); //Actualizar numeros de cajas
        
        return dto;
    }
}
