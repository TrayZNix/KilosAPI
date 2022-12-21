package com.grupocinco.kilosapi.controller;

import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.service.AportacionService;
import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.aportacion.AportacionDto;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import com.grupocinco.kilosapi.service.DetalleAportacionService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aportacion")
@Tag(name = "Aportación", description = "Controlador con las peticiones relacionadas con la aportación: obtención, creación, edición y eliminación de aportaciones")
public class AportacionController {
    private final AportacionService aportacionService;

    private final DetalleAportacionService detalleAportacionService;

    @Autowired
    private AportacionService serviceA;

    @Operation(
            summary = "Obtener una aportación",
            description = "Esta petición devuelve la aportación con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La aportación existe",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseInfoAportacionDto.class)), examples = @ExampleObject("""
                            {
                                "claseId": 11,
                                "aportaciones": [
                                    {
                                        "fecha": "2022-12-21",
                                        "detalleAportaciones": [
                                            {
                                                "nombreTipoAlimento": "Azúcar",
                                                "cantidadKg": 10.0
                                            }
                                        ]
                                    },
                                    {
                                        "fecha": "2022-12-21",
                                        "detalleAportaciones": [
                                            {
                                                "nombreTipoAlimento": "Leche",
                                                "cantidadKg": 10.0
                                            }
                                        ]
                                    }
                                ]
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La aportación no existe",
                    content = {@Content()}
            )
    })
    @GetMapping("clase/{id}")
    public ResponseEntity<ClaseInfoAportacionDto> getAportacionByClaseId(@Parameter(name = "Id de la clase", description = "Id de la clase a buscar aportaciones") @PathVariable Long id) {
        Optional<ClaseInfoAportacionDto> clase = aportacionService.aportacionDetalleByClaseId(id); //FIXME se ponen tantas aportaciones como detalles tenga
        if (clase.isPresent())
            return ResponseEntity.ok().body(clase.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    //TODO comprobar que esto funciona cuando se puedan hacer cosas con las aportaciones y los detalles de aportación
    public ResponseEntity<Aportacion> deleteAportacionById(@Parameter(name = "Id de la aportación", description = "Id de la aportación a eliminar") @PathVariable Long id) {
        if (aportacionService.existsById(id))
            aportacionService.deleteById(id);
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Elimina un detalle de aportación de una aportación",
            description = "Esta petición elimina un detalle de aportación con el numero de detalle indicado de una aportación con id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se encontró la aportación con el id indicado y con detalle de aportación con num indicado",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseInfoAportacionDto.class)), examples = @ExampleObject("""
                            {
                                "claseId": 11,
                                "aportaciones": [
                                    {
                                        "fecha": "2022-12-21",
                                        "detalleAportaciones": [
                                            {
                                                "nombreTipoAlimento": "Azúcar",
                                                "cantidadKg": 10.0
                                            }
                                        ]
                                    }
                                ]
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No existe el detalle de aportación porque el id, el num o ambos son incorrectos",
                    content = {@Content()}
            )
    })
    @DeleteMapping("{id}/linea/{num}")
    public ResponseEntity<ClaseInfoAportacionDto> deleteDetalleAportacionByLineaAndId(@Parameter(name = "Id de la aportación", description = "Id de la aportación cuyo detalle queremos eliminar") @PathVariable Long id,
                                                                                      @Parameter(name = "Num de detalle de la aportación", description = "Num de detalle de aportación a eliminar") @PathVariable Long num) {
        Optional<DetalleAportacion> detalleAportacionOptional = detalleAportacionService.findDetalleAportacionByAportacionIdAndLinea(id, num);
        DetalleAportacion detalleAportacion;
        Aportacion aportacion;
        Optional<ClaseInfoAportacionDto> clase;

        if (detalleAportacionOptional.isPresent()) {
            detalleAportacion = detalleAportacionOptional.get();
            aportacion = detalleAportacion.getAportacion();
            aportacion.removeDetalleAportacion(detalleAportacion);
            aportacionService.save(aportacion);
            clase = aportacionService.aportacionDetalleByClaseId(aportacion.getClase().getIdClase());

            if (clase.isPresent())
                return ResponseEntity.ok().body(clase.get());
            else
                return ResponseEntity.badRequest().build();
        } else
            return ResponseEntity.badRequest().build();
    }

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
