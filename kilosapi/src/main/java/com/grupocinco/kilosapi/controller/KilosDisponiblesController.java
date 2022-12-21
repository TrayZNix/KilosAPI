package com.grupocinco.kilosapi.controller;


import com.grupocinco.kilosapi.dto.kilosDisponibles.KilosDisponiblesDto;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.model.DetalleAportacion;
import com.grupocinco.kilosapi.model.KilosDisponibles;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.repository.AportacionRepository;
import com.grupocinco.kilosapi.repository.KilosDisponiblesRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.AportacionService;
import com.grupocinco.kilosapi.service.KilosDisponiblesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

@RestController()
@RequestMapping("/kilosDisponibles")
@Tag(name = "Kilos Disponibles", description = "Controlador que manejará peticiones de los kilos disponibles")
public class KilosDisponiblesController {

    @Autowired
    private KilosDisponiblesRepository repo;

    @Autowired
    private KilosDisponiblesService kilosDispService;

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepo;

    @Autowired
    private AportacionRepository aportacionRepository;


    @Operation(description = "Devuelve una lista de todos los kilos disponibles guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado algunos kilos disponibles",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                               {
                                                    "id": 1,
                                                    "tipoAlimento": ,
                                                    "cantidadDisponible": 18.5
                                               },
                                               {
                                                    "id": 2,
                                                    "tipoAlimento": ,
                                                    "cantidadDisponible": 20
                                               },
                                               {
                                                    "id": 3,
                                                    "tipoAlimento": ,
                                                    "cantidadDisponible": 11.75
                                               }
                                            ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron kilos disponibles",
                    content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<List<KilosDisponiblesDto>> getListaDatos(){
        List<KilosDisponibles> lista = repo.findAll();
        KilosDisponiblesDto kilos = new KilosDisponiblesDto();
        List<KilosDisponiblesDto> listaDto = new ArrayList<>();
        lista.forEach(k -> listaDto.add(kilos.of(k)));
        if(lista.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listaDto);
    }


    @Operation(description = "Devuelve una lista de todos los kilos disponibles guardados de un tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado algunos kilos disponibles de dicho alimento",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                               {
                                                    "id": 1,
                                                    "lineaDetalle": ,
                                                    "kgsTipoAlimento": 18.5
                                               }
                                            ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron kilos disponibles de dicho alimento",
                    content = {@Content})
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<Aportacion>> getListaKilosUnAlimento(@PathVariable Long id){

        Optional<TipoAlimento> optionalTipoAli = tipoAlimentoRepo.findById(id);

        if(optionalTipoAli.isPresent()){
            TipoAlimento tipoAlimento = optionalTipoAli.get();

            List<DetalleAportacion> listaDetallesAportacion = kilosDispService.findDetalleAportacionByTipoAlimentoId(tipoAlimento);

            List<Aportacion> listaAportacion = new ArrayList<>();
            listaDetallesAportacion.forEach(d -> listaAportacion.add(d.getAportacion()));

            return ResponseEntity.status(HttpStatus.OK).body(listaAportacion);

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }





}
