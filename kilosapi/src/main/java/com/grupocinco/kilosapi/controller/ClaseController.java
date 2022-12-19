package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.dto.view.ClaseViews;
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
@RequestMapping("/clase")
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
                                "id": 11,
                                "nombre": "2º DAM",
                                "tutor": "Luismi",
                                "numAportaciones": 0,
                                "numKilos": 0.0
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
    public ResponseEntity<ClaseDetalleDto> getClaseById(@Parameter(name = "Id de la clase", description = "Id de la clase a buscar") @PathVariable Long id) {
        Optional<ClaseDetalleDto> clase = claseService.claseDetalleById(id); //TODO comprobar que va cuando se puedan tener kilos y esas cosas
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
                    description = "Se creó la clase",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClaseViews.NewClase.class), examples = @ExampleObject("""
                            {
                                "id": 11,
                                "nombre": "2º DAM",
                                "tutor": "Luismi"
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para crear una nueva clase",
                    content = {@Content()}
            )
    })
    @JsonView(ClaseViews.NewClase.class)
    @PostMapping("")
    //TODO comprobar que la consulta funciona cuando se puedan hacer cosas con aportaciones y detalles de aportaciones
    public ResponseEntity<Clase> newClase(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creación de una nueva clase", content = @Content(examples = @ExampleObject("""
            {
                "nombre": "2º DAM",
                "tutor": "Luismi"
            }
            """))) @JsonView(ClaseViews.NewClase.class) @RequestBody Clase clase) {
        if (clase.getNombre()  == null|| clase.getTutor() == null)
            return ResponseEntity.badRequest().build();
        else {
            Clase claseEntera = Clase.builder().nombre(clase.getNombre()).tutor(clase.getTutor()).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(claseService.save(claseEntera));
        }
    }

    @Operation(
            summary = "Edita una clase",
            description = "Esta petición edita una clase con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó la clase",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClaseViews.NewClase.class), examples = @ExampleObject("""
                            {
                                "id": 11,
                                "nombre": "2º DAM",
                                "tutor": "Luismi"
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para editar una clase",
                    content = {@Content()}
            )
    })
    @JsonView(ClaseViews.NewClase.class)
    @PutMapping("{id}")
    public ResponseEntity<Clase> editClase(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la edición de una clase", content = @Content(examples = @ExampleObject("""
            {
                "nombre": "2º DAM",
                "tutor": "Luismi"
            }
            """))) @JsonView(ClaseViews.NewClase.class) @RequestBody Clase clase, @Parameter(name = "Id de la clase", description = "id de la clase a editar") @PathVariable Long id) {
        Optional<Clase> claseEdit = claseService.findById(id);
        Clase claseObt;

        if (claseEdit.isPresent()) {
            claseObt = claseEdit.get();
            if (clase.getNombre() == null && clase.getTutor() == null) {
                return ResponseEntity.badRequest().build();
            } else {
                if (clase.getNombre() != null)
                    claseObt.setNombre(clase.getNombre());
                if (clase.getTutor() != null)
                    claseObt.setTutor(clase.getTutor());
                return ResponseEntity.ok().body(claseService.save(claseObt));
            }
        } else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Elimina una clase",
            description = "Esta petición elimina la clase que contenga el id indicado, si no existe no hace nada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe la clase",
                    content = {@Content()}
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Clase> deleteClase(@Parameter(name = "Id de clase", description = "Id de la clase a eliminar") @PathVariable Long id) {
        if (claseService.existsById(id))
            claseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
