package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.service.DestinatarioService;
import com.grupocinco.kilosapi.view.DestinatarioViews;
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

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/destinatario")
@Tag(name = "Destinatarios", description = "Controlador que manejará peticiones de objetos destinatario")
public class DestinatarioController {
    @Autowired
    private DestinatarioRepository repoDestinatarios;
    @Autowired
    private CajaRepository repoCaja;
    @Autowired
    private DestinatarioService servDest;
    @Operation(description = "Devuelve una lista de todos los destinatarios guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró uno o más destinatarios",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                        "id": 1,
                                                        "totalKilos": 50.93,
                                                        "numerosCaja": [
                                                            1,
                                                            2
                                                        ]
                                                },{
                                                        "id": 2,
                                                        "totalKilos": 17.57,
                                                        "numerosCaja": [
                                                            3
                                                        ]
                                                }
                                            ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content})
    })
    @GetMapping()
    @JsonView(DestinatarioViews.DestinatarioList.class)
    public ResponseEntity<List<Destinatario>> getListaDestinatarios(){
        List<Destinatario> lista = repoDestinatarios.findAll();
        lista.forEach(destinatario -> servDest.calculosDestinatario(destinatario));

        if(lista.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(lista);
    }

    @Operation(description = "Devuelve un destinatario según su id guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 2,
                                                "nombre": "Hermanitas de los Pobres",
                                                "direccion": "C/ Luis Montoto 43",
                                                "personaContacto": "José",
                                                "telefono": "954543092",
                                                "cajas": [
                                                    {
                                                        "id": 5,
                                                        "numeroCaja": 3,
                                                        "totalKilos": 17.57
                                                    }
                                                ],
                                                "totalKilos": 17.57,
                                                "cantidadCajas": 1
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraro el destinatario",
                    content = {@Content})
    })
    @GetMapping("/{id}")
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    public ResponseEntity<Destinatario> getDestinatarioConcreto(@PathVariable Long id){
        Optional<Destinatario> optDest = repoDestinatarios.findById(id);
        if (optDest.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            Destinatario d = optDest.get();
            return ResponseEntity.ok(servDest.calculosDestinatario(d));
        }

    }
    @Operation(description = "Devuelve los detalles de un destinatario según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            "id": 2,
                                                "nombre": "Hermanitas de los Pobres",
                                                "direccion": "C/ Luis Montoto 43",
                                                "personaContacto": "José",
                                                "telefono": "954543092",
                                                "totalKilos": 17.57,
                                                "cantidadCajas": 1
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron destinatarios",
                    content = {@Content})
    })
    @GetMapping("/{id}/detalle")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<Destinatario> getDestinatarioConcretoDetallado(@PathVariable Long id){
        Optional<Destinatario> optDest = repoDestinatarios.findById(id);
        if (optDest.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            Destinatario d = optDest.get();
            return ResponseEntity.ok(servDest.calculosDestinatario(d));
        }
    }

    @Operation(description = "Crea un destinatario según el cuerpo que le mandamos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Destinatario.class),
                    examples = @ExampleObject(value = """
                            {
                                                "nombre": "Comedor Don Bosco",
                                                "direccion": "C/ Condes de Bustillo 13",
                                                "personaContacto": "Julio Vera",
                                                "telefono": "954742432"
                                            }
                            """)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se encontró el destinatario",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 6,
                                                "nombre": "Comedor Don Bosco",
                                                "direccion": "C/ Condes de Bustillo 13",
                                                "personaContacto": "Julio Vera",
                                                "telefono": "954742432"
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Alguno de los campos no tiene el tipo de dato requerido, o es nulo",
                    content = {@Content})
    })
    @PostMapping("")
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    public ResponseEntity<Destinatario> createDestinatario(@RequestBody @JsonView(DestinatarioViews.ModeloPostDestinatario.class) Destinatario d){
        Destinatario dest = Destinatario.builder()
                .nombre(d.getNombre())
                .telefono(d.getTelefono())
                .direccion(d.getDireccion())
                .personaContacto(d.getPersonaContacto())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(repoDestinatarios.save(dest));
    }
    @Operation(description = "Modifica un destinatario según el id introducido en la url y el cuerpo que le mandamos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Destinatario.class),
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
                                                "id": 6,
                                                "nombre": "Comedor Salesianos Triana",
                                                "direccion": "C/ Condes de Bustillo 17",
                                                "telefono": "954331488",
                                                "personaContacto": "Julio Vera"
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Alguno de los campos no tiene el tipo de dato requerido, o es nulo",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "No se encontró el destinatario a editar")
    })
    @PutMapping("/{id}")
    @JsonView(DestinatarioViews.ModeloPostDestinatario.class)
    public ResponseEntity<Destinatario> createDestinatario(@RequestBody @JsonView(DestinatarioViews.ModeloPostDestinatario.class) Destinatario d, @PathVariable Long id){
        Optional<Destinatario> optD = repoDestinatarios.findById(id);
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
        return ResponseEntity.status(HttpStatus.OK).body(repoDestinatarios.save(dest));
            }
        }
    }

    @Operation(description = "Borra un destinatario segun su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha ejecutado la orden de borrado",
                    content = {@Content})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestinatario(@PathVariable Long id){
        Optional<Destinatario> optDest = repoDestinatarios.findById(id);
        if(optDest.isPresent()){
            Destinatario d = optDest.get();
            repoCaja.deleteRelacionesCajasDestinatarioBorrado(d);
            repoDestinatarios.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
