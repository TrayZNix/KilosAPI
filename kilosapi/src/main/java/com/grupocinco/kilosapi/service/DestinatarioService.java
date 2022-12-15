package com.grupocinco.kilosapi.service;

import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DestinatarioService {
    @Autowired
    private DestinatarioRepository repo;

    public Destinatario calculosDestinatario(Destinatario d){
        d = calcularKgTotales(d);
        d = getNumerosCajas(d);
        d.setCantidadCajas(d.getNumerosCaja().length); //Actualizar numeros de cajas
        System.out.println(d);
        return d;
    }

    public Destinatario calcularKgTotales(Destinatario d){
            List<Caja> cajas = d.getCajas();
            double total = 0;
            for(Caja caja: cajas){
                total = total + caja.getTotalKilos();
            }
            d.setTotalKilos(total);
        return d;

    }

    public Destinatario getNumerosCajas(Destinatario d){
        List<Caja> cajas = d.getCajas();
        List<Integer> numeros = new ArrayList<Integer>();
        for(Caja caja: cajas){
            numeros.add(caja.getNumeroCaja());
        }
        int[] arr = numeros.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
        d.setNumerosCaja(arr);
        return d;
    }
}
