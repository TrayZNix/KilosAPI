package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.aportacion.AportacionDto;
import com.grupocinco.kilosapi.dto.tipoAlimento.TipoAlimentoDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.service.AportacionService;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Aportacion", description = "Controlador de Aportaciones")
public class AportacionController {
    @Autowired
    private AportacionService serviceA;

    //================================================
    //GET LISTA APORTACION
    //================================================
    @Operation(summary = "Obtiene una lista de todas las aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todas las aportaciones",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AportacionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                             "id": 13,
                                                             "fecha": "17/12/2022",
                                                             "nombreClase": "Clase tal",
                                                             "kilosTotales": 23.0
                                                         },
                                                         {
                                                             "id": 14,
                                                             "fecha": "21/12/2022",
                                                             "nombreClase": "Clase tal 2",
                                                             "kilosTotales": 45.6
                                                         }
                                            ]                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna aportacion",
                    content = @Content),
    })
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

    //================================================
    //GET APORTACION POR ID
    //================================================
    @Operation(summary = "Obtiene una aportacion por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la aportacion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 13,
                                                    "fecha": "17/12/2022",
                                                    "nombreClase": "Clase tal",
                                                    "kilosTotales": 24.0,
                                                    "detalleAportaciones": null
                                                }        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna aportacion",
                    content = @Content),
    })
    @GetMapping("/{id}")
    @JsonView(AportacionViews.AportacionById.class)
    public ResponseEntity<AportacionDto> getAportacionById(
            @Parameter(description = " ID del tipo aportacion a consultar")
            @PathVariable Long id){
        Optional<Aportacion> a = serviceA.findById(id);
        if(a.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            AportacionDto result = AportacionDto.of(a.get());
            return ResponseEntity.ok(result);
        }
    }
}
