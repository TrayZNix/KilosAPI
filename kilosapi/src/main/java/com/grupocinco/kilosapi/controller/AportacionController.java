package com.grupocinco.kilosapi.controller;

import com.grupocinco.kilosapi.dto.aportacion.DetalleAportacionDto;
import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.AportacionService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aportacion")
@Tag(name = "Aportación", description = "Controlador con las peticiones relacionadas con la aportación: obtención, creación, edición y eliminación de aportaciones")
public class AportacionController {
    private final AportacionService aportacionService;

    private TipoAlimentoRepository repoTipoAlimento;

    @Operation(
            summary = "Obtener una aportación",
            description = "Esta petición devuelve la aportación con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La aportación existe", //TODO hay que poner el ejemplo de schema cuando se pueda probar
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
                    description = "La aportación no existe",
                    content = {@Content()}
            )
    })
    @GetMapping("{id}") //TODO comprobar que esto funciona cuando se puedan hacer cosas con las aportaciones y los detalles de aportación
    public ResponseEntity<ClaseInfoAportacionDto> getAportacionByClaseId(@Parameter(name = "Id de la aportación", description = "Id de la aportación a buscar") @PathVariable Long id) {
        Optional<ClaseInfoAportacionDto> clase = aportacionService.aportacionDetalleByClaseId(id);
        if (clase.isPresent())
            return ResponseEntity.ok().body(clase.get());
        else
            return ResponseEntity.notFound().build();
    }



    @Operation(
            summary = "Crear una aportación",
            description = "Esta petición crea una aportación nueva"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Aportación creada",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseDetalleDto.class)), examples = @ExampleObject("""
                            {
                                
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Aportación no creada",
                    content = {@Content()}
            )
    })
    @PostMapping()
    public ResponseEntity<Aportacion> createAportacion(@Parameter(name = "Nueva aportación insertada", description = "Cuerpo de petición de la nueva aportación insertada") @RequestBody DetalleAportacionDto dto){



        return ResponseEntity.badRequest().build();

    }
//
//    @PutMapping("{id}/linea/{num}/kg/{numKg}")
//    public ResponseEntity<?> editAportacion(@PathVariable Long id, @PathVariable Long num, @PathVariable Double numKg){
//
//
//    }



    @Operation(
            summary = "Borra una aportación",
            description = "Esta petición borra una aportación"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Sin contenido",
                    content = {@Content()}
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAportacionById(@Parameter(name = "Id de la aportación", description = "Id de la aportación a eliminar") @PathVariable Long id) {
        if (aportacionService.existsById(id))
            aportacionService.deleteById(id);
        return ResponseEntity.notFound().build();
    }
}
