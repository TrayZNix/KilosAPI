package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaDeleteDto;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.dto.caja.NewCajaDto;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.TieneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    private TipoAlimentoRepository repoTipoAli;
    private CajaService cajaService;

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
    @PostMapping("")
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
    @DeleteMapping("/{id1}/tipo/{idTipoAlim}")
    public ResponseEntity<?> deleteCaja(@PathVariable Long id1, @PathVariable Long idTipoAlim){
        Optional<Caja> caja = repoCaja.findById(id1);

        if(caja.isPresent()){
            Caja c = caja.get();

            Optional<TipoAlimento> tipoAlimentoOptional = repoTipoAli.findById(idTipoAlim);

            if(tipoAlimentoOptional.isPresent()) {
                TipoAlimento ta = tipoAlimentoOptional.get();

                Tiene tiene = cajaService.getAlimentoEnCaja(ta, c);

                repoCaja.deleteById(id1);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(CajaDeleteDto.builder()
                                .id(ta.getId())
                                .name(ta.getNombre())
                                .cantKilos(tiene.getCantidadKgs())
                                .build()
                        );
            }
        }
        return ResponseEntity.ok().build();

    }

}
