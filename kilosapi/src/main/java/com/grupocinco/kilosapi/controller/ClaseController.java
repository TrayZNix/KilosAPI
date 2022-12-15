package com.grupocinco.kilosapi.controller;

import com.grupocinco.kilosapi.dto.clase.ClaseViews;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clase/")
@Tag(name = "ClasesController", description = "Controlador con las peticiones relacionadas con la clase: obtención, creación, edición y eliminación de clases")
public class ClaseController {
    private ClaseService claseService;

    @Operation(
            summary = "Obtener todas las clases",
            description = "Esta petición devuelve una lista con todas las clases"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La base de datos contiene clases",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseViews.AllClases.class)), examples = @ExampleObject("""
                            [
                                {
                                    "id": 1,
                                    "name": "Joaquín Sabina"
                                },
                                {
                                    "id": 2,
                                    "name": "Dua Lipa"
                                },
                                {
                                    "id": 3,
                                    "name": "Metallica"
                                }
                            ]
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content()}
            )
    })
    @GetMapping("")
    public ResponseEntity<List<Clase>> getAllClases() {
        List<Clase> clases = claseService.findAll();
        if (clases.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(clases);
    }
}
