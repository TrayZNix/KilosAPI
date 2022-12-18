package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.aportacion.AportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.service.AportacionService;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aportacion/")
public class AportacionController {
    @Autowired
    private AportacionService serviceA;

    @GetMapping("")
    @JsonView(AportacionViews.ListaAportacion.class)
    public ResponseEntity<List<AportacionDto>> getAllAportacioness(){
        List<Aportacion> data = serviceA.findAll();
        if(data.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            List<AportacionDto> result = new ArrayList<>();
            for (Aportacion a: data) result.add(AportacionDto.of(a));
            return ResponseEntity.ok(result);
        }

    }

    @GetMapping("/{id}")
    @JsonView(AportacionViews.AportacionById.class)
    public ResponseEntity<AportacionDto> getAportacionById(@PathVariable Long id){
        Optional<Aportacion> a = serviceA.findById(id);
        if(a.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            AportacionDto result = AportacionDto.of(a.get());
            return ResponseEntity.ok(result);
        }
    }
}
