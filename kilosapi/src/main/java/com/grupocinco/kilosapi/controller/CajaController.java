package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.CajaDto;
import com.grupocinco.kilosapi.dto.caja.CajaMapper;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.dto.view.CajaViews;
import com.grupocinco.kilosapi.dto.view.ClaseViews;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.dtos.NewCajaDto;
import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.TieneRepository;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.repository.*;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.TieneService;
import com.grupocinco.kilosapi.service.TipoAlimentoService;
import com.grupocinco.kilosapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private CajaService cajaService;
    @Autowired
    private TieneService servTiene;
    @Autowired
    private TieneService servicetiene;
    @Autowired
    private TipoAlimentoRepository repoTipoAl;
    @Autowired
    private TipoAlimentoService tipoAlimentoService;
    @Autowired
    private DestinatarioService servDest;
    @Autowired
    private KilosDisponiblesService servKilos;
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
        List<Caja> listaCajas = servCaja.findAll();
        if(listaCajas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(mapperCaja.toListCajaDto(listaCajas));
    }

    @Operation(description = "Añade la cantidad deseada del tipo de alimento indicado, a la caja de id proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se añadió correctamente",
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
                    description = "No se encontró la caja donde añadir los alimentos",
                    content = {@Content})
    })
    @PostMapping("/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<CajaDto> addAlimentoToCaja(@PathVariable Long id, @PathVariable Long idTipoAlim, @PathVariable Double cantidad){
        Optional<Caja> optCaja = servCaja.findById(id);
        if(optCaja.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            Caja c = optCaja.get();
            Optional<TipoAlimento> optTipo = servTipoAlim.findById(idTipoAlim);
            if (optTipo.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else{
                TipoAlimento t = optTipo.get();
                Double cantidadDisponible = servKilos.getKilosByTipoRelacionado(t);
                if(cantidad > cantidadDisponible) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                else{
                    servKilos.setKilosDisponiblesToTipoRelacionado(t, -cantidad);
                    TienePK tPk = TienePK.builder().cajaId(c.getId()).tipoAlimentoId(t.getId()).build();
                    Optional<Double> optCantidadExist = servTiene.findIfAlreadySavedTipoAlimentoInCaja(t, c);
                    if(optCantidadExist.isEmpty()){
                        Tiene ti = Tiene.builder()
                                .id(tPk)
                                .caja(c)
                                .tipoAlimento(t)
                                .cantidadKgs(cantidad)
                                .build();
                        servicetiene.saveLinea(ti);
                    }
                    else{
                        Tiene ti = Tiene.builder()
                                .id(tPk)
                                .caja(c)
                                .tipoAlimento(t)
                                .cantidadKgs(cantidad + optCantidadExist.get())
                                .build();
                        servicetiene.saveLinea(ti);
                    }
                    CajaDto cdto = CajaDto.of(c);
                    cdto.setContenido(mapperTiene.ofList(servTiene.getLineasCajas(c)));
                    return ResponseEntity.status(HttpStatus.CREATED).body(cdto);
                }
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
        return ResponseEntity.status(HttpStatus.CREATED).body(servCaja.save(ca));
    }

    @Operation(
            summary = "Edita kg de una caja",
            description = "Esta petición edita los kg de un tipo de alimento de una caja"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó la caja",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClaseViews.NewClase.class), examples = @ExampleObject("""
                            {
                                "id": 3,
                                "numeroCaja": 1,
                                "totalKilos": 2.6,
                                "contenido": [
                                    {
                                        "tipoAlimento": {
                                            "id": 6,
                                            "nombre": "Arroz"
                                        },
                                        "cantidadKgs": 1.0
                                    }
                                ]
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para editar una caja",
                    content = {@Content()}
            )
    })
    @PutMapping("{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<CajaDto> editKgCaja(@Parameter(name = "Id de la aportación", description = "id de la aportación a editar") @PathVariable Long id,
                                              @Parameter(name = "Id del tipo de alimento", description = "id del tipo de alimento a editar") @PathVariable Long idTipoAlim,
                                              @Parameter(name = "Cantidad kg", description = "Cantidad de kg a tener en una caja si la cantidad disponible del tipo indicado lo permite.") @PathVariable Double cantidad) {
        Optional<Caja> cajaOpt = cajaService.getCajaByIdAndIdTipo(id, idTipoAlim); //FIXME solo devuelve el tipo de alimento con el id que se pasa, si tiene más tipos no se muestran
        Caja caja;
        double dif;
        TipoAlimento tipoAlimento;
        CajaMapper mapper = new CajaMapper();
        Double kgCaja;

        if (cajaOpt.isPresent()) {
            caja = cajaOpt.get();
            kgCaja = caja.getLineas().stream().filter(linea -> linea.getTipoAlimento().getId().equals(idTipoAlim)).findFirst().get().getCantidadKgs();
            cajaService.actualizarDatosCajas(List.of(caja));
            dif = kgCaja - cantidad;
            if (cantidad >= 0) {
                if (dif != 0) {
                    tipoAlimento = caja.getLineas().stream().filter(linea -> linea.getTipoAlimento().getId().equals(idTipoAlim)).findFirst().get().getTipoAlimento();
                    if (dif < 0) {
                        if (tipoAlimento.getKilosDisponible().getCantidadDisponible() >= -dif) {
                            tipoAlimentoService.save(tipoAlimento.sumKilos(dif));
                            caja.getLineas().stream().filter(linea -> linea.getTipoAlimento().getId().equals(idTipoAlim)).findFirst().get().setCantidadKgs(cantidad);
                        }
                    } else {
                        tipoAlimentoService.save(tipoAlimento.sumKilos(dif));
                        caja.getLineas().stream().filter(linea -> linea.getTipoAlimento().getId().equals(idTipoAlim)).findFirst().get().setCantidadKgs(cantidad);
                    }
                }
            }
            cajaService.save(caja);
            return ResponseEntity.ok().body(mapper.toCajaDto(caja));
        } else
            return ResponseEntity.badRequest().build();
    }

    @Operation(description = "Borra una caja y la lista de alimentos que contiene")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Caja borrada satisfactoriamente",
                    content = {@Content})
    })
    @DeleteMapping("/caja/{id1}/tipo/{idTipoAlim}")
    public ResponseEntity<?> deleteCaja(@PathVariable Long id1, @PathVariable Long idtipoAlim){
        Optional<Caja> caja = servCaja.findById(id1);
        if(caja.isPresent()){
            Caja c = caja.get();

            servCaja.deleteById(id1);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/{id}/destinatario/{idDestinataro}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetalles.class)
    public ResponseEntity<CajaDto> asignarDestinatarioACaja(@PathVariable Long id, @PathVariable Long idDestinataro) {
        Optional<Caja> optC = servCaja.findById(id);
        Optional<Destinatario> optD = servDest.findById(idDestinataro);
        if(optC.isPresent()){
            if (optD.isPresent()){
                Caja c = optC.get();
                Destinatario d = optD.get();
                c.setDestinatario(d);

                return ResponseEntity.ok(mapperCaja.toCajaDto(c));
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
