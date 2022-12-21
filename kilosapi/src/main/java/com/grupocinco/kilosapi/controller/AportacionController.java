package com.grupocinco.kilosapi.controller;


import com.grupocinco.kilosapi.dto.aportacion.DetalleAportacionDto;
import com.grupocinco.kilosapi.dto.clase.ClaseDetalleDto;
import com.grupocinco.kilosapi.dto.clase.ClaseInfoAportacionDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;


import com.grupocinco.kilosapi.service.AportacionService;
import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.aportacion.AportacionDto;
import com.grupocinco.kilosapi.dto.view.AportacionViews;
import com.grupocinco.kilosapi.service.DetalleAportacionService;
import com.grupocinco.kilosapi.service.KilosDisponiblesService;
import com.grupocinco.kilosapi.service.TipoAlimentoService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aportacion")
@Tag(name = "Aportación", description = "Controlador con las peticiones relacionadas con la aportación: obtención, creación, edición y eliminación de aportaciones")
public class AportacionController {
    private final AportacionService aportacionService;

    private final TipoAlimentoService tipoAlimentoService;
    private final DetalleAportacionService detalleAportacionService;

    private final KilosDisponiblesService kilosDisponiblesService;

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
        Optional<ClaseInfoAportacionDto> clase = aportacionService.aportacionDetalleByClaseId(id); //FIXME Luismi, esto duplica las aportaciones por un error de hibernate,
        // se podría solucionar con criterios, pero al no haberlos visto, prefiero dejarlo como un fixme por si los vemos más adelante
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
    @JsonView(AportacionViews.AportacionResponse.class)
    @PostMapping()
    public ResponseEntity<AportacionDto> createAportacion(@Parameter(name = "Nueva aportación insertada", description = "Cuerpo de petición de la nueva aportación insertada") @RequestBody DetalleAportacionDto dto){

        Optional<TipoAlimento> optional = tipoAlimentoService.findById(dto.getIdTipoAlimento());

        if(optional.isPresent()){

            TipoAlimento alimento = optional.get();

            Aportacion aportacion = Aportacion.builder()
                    .fecha(LocalDate.now())
                    .build();

            aportacionService.save(aportacion);

            DetalleAportacion detalleAportacion = DetalleAportacion.builder()
                    .detalleAportacionId(DetalleAportacion.DetalleAportacionId.builder().build())
                    .tipoAlimento(alimento)
                    .cantidad_en_kgs(dto.getCantidadKilos())
                    .build();

            List<DetalleAportacion> listaDetAport = new ArrayList<DetalleAportacion>();

            listaDetAport.add(detalleAportacion);

            aportacion.setDetalles(listaDetAport);

            aportacionService.save(aportacion);



            return ResponseEntity.status(HttpStatus.CREATED).body(AportacionDto.builder()
                            .nombreAlimento(alimento.getNombre())
                            .numLinea(detalleAportacion.getDetalleAportacionId().getNumLinea())
                            .kilosTotales(detalleAportacion.getCantidad_en_kgs())
                            .build()
            );
        }else{
            return ResponseEntity.badRequest().build();
        }

    }


    @Operation(
            summary = "Editar una aportación",
            description = "Esta petición edita una aportación"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Aportación editada",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClaseDetalleDto.class)), examples = @ExampleObject("""
                            {
                                
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Aportación no editada",
                    content = {@Content()}
            )
    })
    @JsonView(AportacionViews.AportacionResponse.class)
    @PutMapping("{id}/linea/{num}/kg/{numKg}")
    public ResponseEntity<AportacionDto> editAportacion(@PathVariable Long id, @PathVariable Long num, @PathVariable Double numKg){

        Optional<Aportacion> aportacionOpt = aportacionService.findById(id);

        if(aportacionOpt.isPresent()){

            Aportacion aport = aportacionOpt.get();

            List<DetalleAportacion> listaDetAport = aport.getDetalles();

            DetalleAportacion detalle = new DetalleAportacion();

            for(DetalleAportacion d: listaDetAport){
                if(d.getDetalleAportacionId().getNumLinea() == num){
                    detalle = d;
                }
            }

            listaDetAport.remove(detalle);

            Double preedit = detalle.getCantidad_en_kgs();

            Double edit = numKg - preedit;

            detalle.setCantidad_en_kgs(preedit + edit);

            listaDetAport.add(detalle);

            aport.setDetalles(listaDetAport);

            aportacionService.save(aport);

            TipoAlimento alimento = detalle.getTipoAlimento();

            Optional<KilosDisponibles> kilosEdit = kilosDisponiblesService.findById(alimento.getId());

            KilosDisponibles k = kilosEdit.get();

            k.setCantidadDisponible(k.getCantidadDisponible() + edit);

            kilosDisponiblesService.save(k);

            AportacionDto dto = AportacionDto.builder()
                    .nombreAlimento(detalle.getTipoAlimento().getNombre())
                    .numLinea(num)
                    .kilosTotales(detalle.getCantidad_en_kgs())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(dto);

        }else{
            return ResponseEntity.badRequest().build();
        }
    }



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
                    content = {@Content(mediaType = "application/json",
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
    public ResponseEntity<List<AportacionDto>> getAllAportacioness() {
        List<Aportacion> data = serviceA.findAll();
        if (data.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            List<AportacionDto> result = new ArrayList<>();
            for (Aportacion a : data) result.add(AportacionDto.of(a));
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
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 13,
                                                    "fecha": "2022-12-20",
                                                    "nombreClase": "Clase tal",
                                                    "kilosTotales": 25.0,
                                                    "detalleAportaciones": [
                                                        {
                                                            "numLinea": 1,
                                                            "nombreAlimento": "Arroz",
                                                            "cantidadKgs": 15.0
                                                        },
                                                        {
                                                            "numLinea": 2,
                                                            "nombreAlimento": "Azúcar",
                                                            "cantidadKgs": 10.0
                                                        }
                                                    ]
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
            @PathVariable Long id) {
        Optional<Aportacion> a = serviceA.findById(id);
        if (a.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            AportacionDto result = AportacionDto.of(a.get());

            return ResponseEntity.ok(result);
        }
    }
}
