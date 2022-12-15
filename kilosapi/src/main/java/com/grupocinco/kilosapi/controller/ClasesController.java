package com.grupocinco.kilosapi.controller;

import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.service.ClasesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clase/")
@Tag(name = "ClasesController", description = "Controlador con las peticiones relacionadas con la clase: obtención, creación, edición y eliminación de clases")
public class ClasesController {
    private ClasesService clasesService;


    @GetMapping("")
    public ResponseEntity<List<Clase>> getAllClases() {
        List<Clase> clases = clasesService.findAll();
        if (clases.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(clases);
    }
}
