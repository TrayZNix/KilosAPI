package com.grupocinco.kilosapi.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.view.CajaViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/caja")
public class CajaController {
    @Autowired
    private CajaRepository repoCaja;

    @GetMapping()
    @JsonView(CajaViews.CajasList.class)
    public ResponseEntity<List<Caja>> getCajas(){
        List<Caja> listaCajas = repoCaja.findAll();
        if(listaCajas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listaCajas);
    }
}
