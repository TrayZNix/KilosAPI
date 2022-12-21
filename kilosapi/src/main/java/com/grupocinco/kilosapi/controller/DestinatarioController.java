package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.DestinatarioService;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController()
@RequestMapping("/destinatario")
@Tag(name = "Destinatarios", description = "Controlador que manejará peticiones de objetos destinatario")
public class DestinatarioController {

    @Autowired
    private DestinatarioService servDest;
    @Autowired
    private CajaService servCaja;
    @Autowired
    private DestinatarioMapper mapperDest;

    @Operation(summary = "Devuelve una lista de todos los destinatarios guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró uno o más destinatarios",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 4,
                                                    "direccion": "C/ Pagés del Corro 34",
                                                    "nombre": "Comedor Pagés del Corro",
                                                    "personaContacto": "María",
                                                    "telefono": "954347087",
                                                    "numerosCaja": [
                                                        1,
                                                        2
                                                    ]
                                                },
                                                {
                                                    "id": 5,
                                                    "direccion": "C/ Luis Montoto 43",
                                                    "nombre": "Hermanitas de los Pobres",
                                                    "personaContacto": "José",
                                                    "telefono": "954543092",
                                                    "numerosCaja": [
                                                        3
                                                    ]
                                                }
                                            ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ningún destinatario",
                    content = {@Content})
    })
    @GetMapping()
    @JsonView(DestinatarioViews.DestinatarioList.class)
    public ResponseEntity<List<DestinatarioDto>> getListaDestinatarios(){
        List<Destinatario> lista = servDest.findAll();
        List<DestinatarioDto> listaDto = new ArrayList<DestinatarioDto>();
        lista.forEach(destinatario -> listaDto.add(servDest.setDatosDestinatarioDto(destinatario)));
        if(lista.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listaDto);
    }

    @Operation(summary = "Devuelve un destinatario según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 4,
                                                 "direccion": "C/ Pagés del Corro 34",
                                                 "nombre": "Comedor Pagés del Corro",
                                                 "personaContacto": "María",
                                                 "telefono": "954347087",
                                                 "totalKilos": 11.4,
                                                 "cantidadCajas": 2
                                             }
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el destinatario",
                    content = {@Content})
    })
    @GetMapping("/{id}")
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    public ResponseEntity<DestinatarioDto> getDestinatarioConcreto(@PathVariable Long id){
        Optional<Destinatario> optDest = servDest.findById(id);
        if (optDest.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.ok(servDest.setDatosDestinatarioDto(optDest.get()));
        }

    }
    @Operation(summary = "Devuelve los detalles de un destinatario según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 4,
                                                "direccion": "C/ Pagés del Corro 34",
                                                "nombre": "Comedor Pagés del Corro",
                                                "personaContacto": "María",
                                                "telefono": "954347087",
                                                "totalKilos": 11.4,
                                                "cantidadCajas": 2,
                                                "cajas": [
                                                    {
                                                        "id": 1,
                                                        "numeroCaja": 1,
                                                        "totalKilos": 5.1,
                                                        "contenido": [
                                                            {
                                                                "id": 6,
                                                                "nombre": "Arroz",
                                                                "cantidad": 2.5
                                                            },
                                                            {
                                                                "id": 7,
                                                                "nombre": "Azúcar",
                                                                "cantidad": 2.6
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": 2,
                                                        "numeroCaja": 2,
                                                        "totalKilos": 6.3,
                                                        "contenido": [
                                                            {
                                                                "id": 7,
                                                                "nombre": "Azúcar",
                                                                "cantidad": 6.3
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el destinatarios",
                    content = {@Content})
    })
    @GetMapping("/{id}/detalle")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<DestinatarioDto> getDestinatarioConcretoDetallado(@PathVariable Long id){
        Optional<Destinatario> optDest = servDest.findById(id);
        if (optDest.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.ok(servDest.setDatosDestinatarioDto(optDest.get()));
        }
    }

    @Operation(summary = "Crea un destinatario según el cuerpo que le mandamos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DestinatarioDto.class),
                    examples = @ExampleObject(value = """
                            {
                                "nombre": "Comedor Don Bosco",
                                "direccion": "C/ Condes de Bustillo 13",
                                "telefono": "954742432",
                                "personaContacto": "Julio Vera"
                            }
                            """)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "direccion": "C/ Condes de Bustillo 13",
                                                "nombre": "Comedor Don Bosco",
                                                "personaContacto": "Julio Vera",
                                                "telefono": "954742432"
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Alguno de los campos no es del tipo de dato requerido, o es nulo",
                    content = {@Content})
    })
    @PostMapping("")
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    public ResponseEntity<DestinatarioDto> createDestinatario(@RequestBody @JsonView(DestinatarioViews.ModeloPostDestinatario.class) DestinatarioDto d){
        DestinatarioDto dest = DestinatarioDto.builder()
                .nombre(d.getNombre())
                .telefono(d.getTelefono())
                .direccion(d.getDireccion())
                .personaContacto(d.getPersonaContacto())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(DestinatarioDto.of(servDest.save(DestinatarioDto.to(dest))));
    }

    @Operation(summary = "Modifica un destinatario según el id introducido en la url y el cuerpo que le mandamos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DestinatarioDto.class),
                    examples = @ExampleObject(value = """
                            {
                                "nombre": "Comedor Salesianos Triana",
                                "direccion": "C/ Condes de Bustillo 17",
                                "telefono": "954331488",
                                "personaContacto": "Julio Vera"
                            }
                            """)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "direccion": "C/ Condes de Bustillo 17",
                                                "nombre": "Comedor Salesianos Triana",
                                                "personaContacto": "Julio Vera",
                                                "telefono": "954331488"
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Alguno de los campos no es del tipo de dato requerido, o es nulo",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "No se encontró el destinatario a editar")
    })
    @PutMapping("/{id}")
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    public ResponseEntity<DestinatarioDto> createDestinatario(@RequestBody @JsonView(DestinatarioViews.ModeloPostDestinatario.class) DestinatarioDto d, @PathVariable Long id){
        Optional<Destinatario> optD = servDest.findById(id);
        if(optD.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            Destinatario dest = optD.get();
            if(dest.getNombre() == null || dest.getDireccion() == null || dest.getTelefono() == null || dest.getPersonaContacto() == null){
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                dest.setTelefono(d.getTelefono());
                dest.setNombre(d.getNombre());
                dest.setDireccion(d.getDireccion());
                dest.setPersonaContacto(d.getPersonaContacto());
        return ResponseEntity.status(HttpStatus.OK).body(DestinatarioDto.of(servDest.save(dest)));
            }
        }
    }

    @Operation(summary = "Borra un destinatario segun su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha ejecutado una orden de borrado",
                    content = {@Content})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestinatario(@PathVariable Long id){
        Optional<Destinatario> optDest = servDest.findById(id);
        if(optDest.isPresent()){
            Destinatario d = optDest.get();
            servCaja.deleteRelacionesCajasDestinatarioBorrado(d);
            servDest.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
