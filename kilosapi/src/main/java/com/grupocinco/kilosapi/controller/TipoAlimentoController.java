package com.grupocinco.kilosapi.controller;

import com.grupocinco.kilosapi.dto.tipoAlimento.TipoAlimentoDto;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.service.KilosDisponiblesService;
import com.grupocinco.kilosapi.service.TieneService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoAlimento/")
@Tag(name = "TipoAlimentos", description = "Controlador de Tipos de alimentos")
public class TipoAlimentoController {

    @Autowired
    private TipoAlimentoService serviceTA;
    @Autowired
    private KilosDisponiblesService serviceK;

    @Autowired
    private TieneService serviceT;

    //================================================
    //GET LISTA TIPO ALIMENTO
    //================================================
    @Operation(summary = "Obtiene una lista de todos los tipos de alimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado tipos de alimento",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TipoAlimentoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                        "id": 6,
                                                        "nombre": "Arroz",
                                                        "cantidad": 20.3
                                                    },
                                                    {
                                                        "id": 7,
                                                        "nombre": "Azúcar",
                                                        "cantidad": 10.0
                                                    }
                                            ]                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun tipo de alimento",
                    content = @Content),
    })
    @GetMapping("")
    public ResponseEntity<List<TipoAlimentoDto>> getListaTipoAlimentos(){
        List<TipoAlimento> data = serviceTA.findAll();

        if(data.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            List<TipoAlimentoDto> result = new ArrayList<TipoAlimentoDto>();
            data.forEach(ta ->{
                result.add(new TipoAlimentoDto(ta.getId(),ta.getNombre(),ta.getKilosDisponible().getCantidadDisponible()));
            });

            return ResponseEntity.ok(result);
        }
    }

    //================================================
    //GET TIPO ALIMENTO BY ID
    //================================================

    @Operation(summary = "Obtiene un tipo de alimento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado tipo de alimento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 8,
                                                    "nombre": "Leche",
                                                    "cantidad": 12.5
                                                }        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun tipo de alimento",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoAlimentoDto> getTipoAlimentoById(
            @Parameter(description = " ID del tipo de alimento a consultar")
            @PathVariable Long id){
        Optional<TipoAlimento> t = serviceTA.findById(id);

        if(t.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            TipoAlimentoDto result = new TipoAlimentoDto(
                    t.get().getId(),
                    t.get().getNombre(),
                    t.get().getKilosDisponible().getCantidadDisponible());
            return ResponseEntity.ok(result);
        }
    }

    //================================================
    //EDIT TIPO ALIMENTO
    //================================================
    @Operation(summary = "Modifica el tipo de alimento con ID especificado por el tipo de alimento recibido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha realizado correctamente la edicion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 6,
                                                    "nombre": "Patata",
                                                    "cantidad": 40.8
                                                }                                                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Ha habido un error en los datos recibidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun tipo de alimento",
                    content = @Content),

    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoAlimentoDto> editTipoAlimento(
            @Parameter(description = " ID del tipo de alimento a editar")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = " Objeto Dto necesario para la creacion del tipo de alimento")
            @RequestBody TipoAlimentoDto dto){


        if (dto.nombre() ==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else{
            Optional<TipoAlimento> t = serviceTA.findById(id);
            if(t.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            else{
                TipoAlimento dataSave = t.map(old ->{
                    old.setNombre(dto.nombre());
                    old.getKilosDisponible().addCantidad(dto.cantidad());
                    return serviceTA.add(old);
                }).orElse(null);

                TipoAlimentoDto result = new TipoAlimentoDto(
                        dataSave.getId(),
                        dataSave.getNombre(),
                        dataSave.getKilosDisponible().getCantidadDisponible());

                return ResponseEntity.ok(result);
            }
        }
    }

    //================================================
    //ADD TIPO ALIMENTO
    //================================================
    @Operation(summary = "Añade un nuevo tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado satisfactoriamente el tipo de alimento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 11,
                                                    "nombre": "Queso",
                                                    "cantidad": 20.5
                                                }                                                                           
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Ha habido un error en los datos recibidos",
                    content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<TipoAlimentoDto> addTipoAlimento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = " Objeto Dto necesario para la creacion del tipo de alimento")
            @RequestBody TipoAlimentoDto dto){
        if(dto.nombre() == null || dto.cantidad() != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        KilosDisponibles k = KilosDisponibles.builder()
                .cantidadDisponible(0.0)
                .build();
        TipoAlimento tAdd = TipoAlimento.builder()
                .nombre(dto.nombre())
                .build();

        tAdd.addToKilosDisponibles(k);

        serviceTA.add(tAdd);
        serviceK.add(k);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TipoAlimentoDto(
                tAdd.getId(),
                tAdd.getNombre(),
                tAdd.getKilosDisponible().getCantidadDisponible()));
    }

    //================================================
    //DELETE TIPO ALIMENTO
    //================================================
    @Operation(summary = "Elimina un tipo de alimento con ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha realizado correctamente la eliminacion, y por tanto no hay contenido",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTipoAlimento(
            @Parameter(description = " ID del tipo de alimento a eliminar")
            @PathVariable Long id){
        if(serviceTA.existsById(id)){
            TipoAlimento t = serviceTA.findById(id).get();
            serviceT.deleteTipoAlimento(t);

            serviceTA.deleteById(id);
        }


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
