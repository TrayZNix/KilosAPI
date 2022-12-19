package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.dto.tipoAlimento.TipoAlimentoDto;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.dtos.NewCajaDto;
import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.TieneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/caja")
public class CajaController {
    @Autowired
    private CajaRepository repoCaja;
    @Autowired
    private TieneRepository repoTiene;
    @Autowired
    private TieneService servicetiene;
    @Autowired
    private TipoAlimentoRepository repoTipoAl;
    @Autowired
    private TieneMapper mapperTiene;
    @Autowired
    private CajaMapper mapperCaja;
    @Autowired
    private CajaService cajaService;

    @Operation(description = "Devuelve una lista de todas las cajas guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró una o más cajas",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                        "id": 3,
                                                        "qr": "qrqrqr",
                                                        "numeroCaja": 1,
                                                        "destinatario": {
                                                            "id": 1,
                                                            "nombre": "Comedor Pagés del Corro"
                                                        }
                                                    },
                                                    {
                                                        "id": 4,
                                                        "qr": "tetete",
                                                        "numeroCaja": 2,
                                                        "destinatario": {
                                                            "id": 1,
                                                            "nombre": "Comedor Pagés del Corro"
                                                        }
                                                    },
                                            ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraró ninguna caja",
                    content = {@Content})
    })
    @GetMapping()
    @JsonView(CajaViews.CajasList.class)
    public ResponseEntity<List<CajaDto>> getCajas(){
        List<Caja> listaCajas = repoCaja.findAll();
        if(listaCajas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(mapperCaja.toListCajaDto(listaCajas));
    }

    @PostMapping("/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<CajaDto> addAlimentoToCaja(@PathVariable Long id, @PathVariable Long idTipoAlim, @PathVariable Double cantidad){
        Optional<Caja> optCaja = repoCaja.findById(id);
        if(optCaja.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            Caja c = optCaja.get();
            Optional<TipoAlimento> optTipo = repoTipoAl.findById(idTipoAlim);
            if (optTipo.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else{
                TipoAlimento t = optTipo.get();
                TienePK tPk = TienePK.builder().cajaId(c.getId()).tipoAlimentoId(t.getId()).build();
                Tiene ti = Tiene.builder().id(tPk).caja(c).tipoAlimento(t).cantidadKgs(cantidad).build();
                servicetiene.saveLinea(ti);
                CajaDto cdto = CajaDto.of(c);
                cdto.setContenido(mapperTiene.ofList(repoTiene.getLineasCajas(c)));
                return ResponseEntity.status(HttpStatus.CREATED).body(cdto);
            }
        }
    }



    @Operation(description = "Crea una caja mediante un cuerpo de petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Caja creada",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "qr": "qrqr",
                                                "numeroCaja": 1 
                                            }
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Caja no creada",
                    content = {@Content})
    })
    @PostMapping("/caja/")
    public ResponseEntity<Caja> createCaja(@RequestBody NewCajaDto dto){

        Caja ca = Caja.builder()
                .qr(dto.getQr())
                .numeroCaja(dto.getNumeroCaja())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(repoCaja.save(ca));
    }

    @Operation(description = "Borra una caja y la lista de alimentos que contiene")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Caja borrada satisfactoriamente",
                    content = {@Content})
    })
    @DeleteMapping("/caja/{id1}/tipo/{idTipoAlim}")
    public ResponseEntity<?> deleteCaja(@PathVariable Long id1, @PathVariable Long idtipoAlim){
        Optional<Caja> caja = repoCaja.findById(id1);
        if(caja.isPresent()){
            Caja c = caja.get();

            repoCaja.deleteById(id1);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Modifica la caja con ID especificado por caja recibida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha realizado correctamente la edicion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 4,
                                                    "numeroCaja": 7,
                                                    "totalKilos": 23.4,
                                                    "contenido": [
                                                        {
                                                            "tipoAlimento": {
                                                                "id": 7,
                                                                "nombre": "Azúcar"
                                                            },
                                                            "cantidadKgs": 6.3
                                                        }
                                                    ]
                                                }                                                                 
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Ha habido un error en los datos recibidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna caja",
                    content = @Content),
    })
    @PutMapping("/{id}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<CajaDto> editCajaById(
            @PathVariable Long id,
            @JsonView(CajaViews.UpdateCaja.class)@RequestBody CajaDto dto){
        Optional<Caja> c = cajaService.findById(id);
        if(c.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            Caja data = c.map(old ->{
                old.setQr(dto.getQr());
                old.setNumeroCaja(dto.getNumeroCaja());
                return cajaService.add(old);
            }).orElse(null);

            CajaDto result = CajaDto.of(c.get());
            result.setContenido(mapperTiene.ofList(repoTiene.getLineasCajas(c.get())));

            return ResponseEntity.ok(result);
        }
    }


    @Operation(summary = "Elimina una Caja con ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha realizado correctamente la eliminacion, y por tanto no hay contenido",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if(cajaService.existById(id)){
            Caja c = cajaService.findById(id).get();
            servicetiene.deleteCaja(c);

            cajaService.deleteById(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
