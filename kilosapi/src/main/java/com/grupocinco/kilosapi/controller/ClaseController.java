package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.dto.clase.ClaseViews;
import com.grupocinco.kilosapi.model.Clase;
import com.grupocinco.kilosapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clase/")
@Tag(name = "Clase", description = "Controlador con las peticiones relacionadas con la clase: obtención, creación, edición y eliminación de clases")
public class ClaseController {
    private final ClaseService claseService;

    @Operation(
            summary = "Obtener todas las clases",
            description = "Esta petición devuelve una lista con todas las clases"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La base de datos contiene clases",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseViews.NewClase.class)), examples = @ExampleObject("""
                            [
                                {
                                    "id": 11,
                                    "nombre": "1º DAM",
                                    "tutor": "Eduardo"
                                },
                                {
                                    "id": 12,
                                    "nombre": "2º DAM",
                                    "tutor": "Luismi"
                                }
                            ]
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron clases",
                    content = {@Content()}
            )
    })
    @JsonView(ClaseViews.NewClase.class)
    @GetMapping("")
    public ResponseEntity<List<Clase>> getAllClases() {
        List<Clase> clases = claseService.findAll();
        clases.forEach(System.out::println);
        if (clases.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(clases);
    }

    @Operation(
            summary = "Obtener una clases",
            description = "Esta petición devuelve la clase con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La clase existe",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseDetalleDto.class)), examples = @ExampleObject("""
                            {
                                "id": 12,
                                "nombre": "2º DAM",
                                "tutor": "Luismi",
                                "aportaciones": []
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La clase no existe",
                    content = {@Content()}
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<ClaseDetalleDto> getClaseById(@Parameter(name = "Id de clase", description = "Id de la clase a buscar") @PathVariable Long id) {
        Optional<ClaseDetalleDto> clase = claseService.claseDetalleById(id);
        if (clase.isPresent())
            return ResponseEntity.ok().body(clase.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear una clase",
            description = "Esta petición crea una nueva clase con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se creo el artista",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClaseViews.NewClase.class), examples = @ExampleObject("""
                            {
                                "id": 13,
                                "name": "Blind Guardian"
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para crear un nuevo artista",
                    content = {@Content()}
            )
    })
    @JsonView(ClaseViews.NewClase.class)
    @PostMapping("")
    public ResponseEntity<Clase> newClase(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creación de una nueva clase", content = @Content(examples = @ExampleObject("""
            {
                "id": 12,
                "nombre": "2º DAM",
                "tutor": "Luismi",
                "aportaciones": []
            }
            """))) @JsonView(ClaseViews.NewClase.class) @RequestBody Clase clase) {
        Clase claseEntera =  Clase.builder().nombre(clase.getNombre()).tutor(clase.getTutor()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.save(claseEntera));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Clase> deleteClase(@Parameter(name = "Id de clase", description = "Id de la clase a eliminar") @PathVariable Long id) {
        claseService.deleteById(id);//TODO comprobar que se pueda hacer sin comprobación
        return ResponseEntity.notFound().build();
    }
}
