package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.caja.*;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioCajaActualizadaDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.dto.tiene.TieneMapper;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Tiene;
import com.grupocinco.kilosapi.model.TipoAlimento;
import com.grupocinco.kilosapi.model.*;
import com.grupocinco.kilosapi.repository.TipoAlimentoRepository;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.TieneService;
import com.grupocinco.kilosapi.service.TipoAlimentoService;
import com.grupocinco.kilosapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/caja")
public class CajaController {

    @Autowired
    private CajaService servCaja;
    @Autowired
    private TieneService servTiene;
    @Autowired
    private TieneService servicetiene;
    @Autowired
    private DestinatarioService servDest;
    @Autowired
    private KilosDisponiblesService servKilos;
    @Autowired
    private TipoAlimentoService tipoAlimentoService;
    @Autowired
    private TieneMapper mapperTiene;
    @Autowired
    private CajaMapper mapperCaja;
    @Autowired
    private DestinatarioMapper mapperDest;
    @Autowired
    private CajaService cajaService;
    @Autowired
    private TipoAlimentoRepository repoTipoAli;

    @Operation(description = "Devuelve una lista de todas las cajas guardados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró una o más cajas",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                  {
                                                      "id": 1,
                                                      "qr": "qr1",
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
                                                      "qr": "qr2",
                                                      "numeroCaja": 2,
                                                      "totalKilos": 6.3,
                                                      "contenido": [
                                                          {
                                                              "id": 7,
                                                              "nombre": "Azúcar",
                                                              "cantidad": 6.3
                                                          }
                                                      ]
                                                  },
                                                  {
                                                      "id": 3,
                                                      "qr": "qr3",
                                                      "numeroCaja": 3,
                                                      "totalKilos": 5.0,
                                                      "contenido": [
                                                          {
                                                              "id": 9,
                                                              "nombre": "Huevo",
                                                              "cantidad": 2.6
                                                          },
                                                          {
                                                              "id": 10,
                                                              "nombre": "Zanahoria",
                                                              "cantidad": 2.4
                                                          }
                                                      ]
                                                  }
                                              ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraró ninguna caja",
                    content = {@Content})
    })
    @GetMapping()
    @JsonView(DestinatarioViews.DestinatarioConcretoDetallesConQr.class)
    public ResponseEntity<List<CajaContenidoDto>> getCajas(){
        List<Caja> listaCajas = servCaja.findAll();
        if(listaCajas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        List<CajaContenidoDto> listaDto = new ArrayList<CajaContenidoDto>();
        listaCajas.forEach(caja -> {
            caja = servCaja.actualizarDatosCajas(caja);
            listaDto.add(mapperCaja.toCajaContenidoDto(caja));
        });
        return ResponseEntity.ok(listaDto);
    }

    @Operation(
            summary = "Obtener una caja",
            description = "Esta petición devuelve la caja con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La caja existe",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CajaDetalleDto.class)), examples = @ExampleObject("""
                            {
                                "id": 1,
                                "qr": "qrqrqr",
                                "numeroCaja": 1,
                                "kilosTotales": null,
                                "destinatario": {
                                    "id": 4,
                                    "nombre": "Comedor Pagés del Corro"
                                },
                                "tiposAlimento": [
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
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La caja no existe",
                    content = {@Content()}
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<CajaDetalleDto> getCajaById(@Parameter(name = "Id de una caja", description = "id de la caja a editar") @PathVariable Long id) {
        Optional<Caja> cajaOptional = cajaService.findById(id); //

        if (cajaOptional.isPresent()) {
            return ResponseEntity.ok().body(CajaDetalleDto.of(cajaOptional.get()));
        } else
            return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Añade un tipo de alimento a la caja determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La operación se efectuó correctamente",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 3,
                                                 "qr": "qr1",
                                                 "numeroCaja": 3,
                                                 "totalKilos": 15.0,
                                                 "contenido": [
                                                     {
                                                         "id": 9,
                                                         "nombre": "Huevo",
                                                         "cantidad": 2.6
                                                     },
                                                     {
                                                         "id": 10,
                                                         "nombre": "Zanahoria",
                                                         "cantidad": 12.4
                                                     }
                                                 ]
                                             }
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró la caja donde añadir los alimentos",
                    content = {@Content}),
            @ApiResponse(responseCode = "400",
                    description = "No se encontró el alimento que añadir a la caja",
                    content = {@Content})
    })
    @PostMapping("/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetallesConQr.class)
    public ResponseEntity<CajaContenidoDto> addAlimentoToCaja(@PathVariable Long id, @PathVariable Long idTipoAlim, @PathVariable Double cantidad){
        Optional<Caja> optCaja = servCaja.findById(id);
        if(optCaja.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            Caja c = optCaja.get();
            Optional<TipoAlimento> optTipo = tipoAlimentoService.findById(idTipoAlim);
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
                    c = servCaja.actualizarDatosCajas(c);
                    CajaContenidoDto cajaDto = mapperCaja.toCajaContenidoDto(c);
                    cajaDto.setContenido(mapperTiene.fromListtoLineaCajaCont(servTiene.getLineasCajas(c)));
                    return ResponseEntity.status(HttpStatus.CREATED).body(cajaDto);
                }
            }
        }
    }

    @Operation(
            summary = "Edita kg de una caja",
            description = "Esta petición edita los kg de un tipo de alimento de una caja"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó la caja",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DestinatarioViews.DestinatarioConcretoDetalles.class), examples = @ExampleObject("""
                            {
                                "id": 1,
                                "numeroCaja": 1,
                                "totalKilos": 5.1,
                                "contenido": [
                                    {
                                        "tipoAlimento": {
                                            "id": 6,
                                            "nombre": "Arroz"
                                        },
                                        "cantidadKgs": 7.4
                                    },
                                    {
                                        "tipoAlimento": {
                                            "id": 7,
                                            "nombre": "Azúcar"
                                        },
                                        "cantidadKgs": 2.6
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
    public ResponseEntity<CajaDto> editKgCaja(@Parameter(name = "Id de la caja", description = "id de la caja a editar") @PathVariable Long id,
                                              @Parameter(name = "Id del tipo de alimento", description = "id del tipo de alimento a editar") @PathVariable Long idTipoAlim,
                                              @Parameter(name = "Cantidad kg", description = "Cantidad de kg a tener en una caja si la cantidad disponible del tipo indicado lo permite.") @PathVariable Double cantidad) {
        Optional<Caja> cajaOpt = servCaja.getCajaByIdAndIdTipo(id, idTipoAlim);
        Caja caja;
        double dif;
        TipoAlimento tipoAlimento;
        Double kgCaja;

        if (cajaOpt.isPresent()) {
            caja = cajaOpt.get();
            kgCaja = caja.getLineas().stream().filter(linea -> linea.getTipoAlimento().getId().equals(idTipoAlim)).findFirst().get().getCantidadKgs();
            servCaja.actualizarDatosCajas(List.of(caja));
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
            servCaja.save(caja);
            return ResponseEntity.ok().body(mapperCaja.toCajaDto(caja));
        } else
            return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/destinatario/{idDestinataro}")
    @JsonView(DestinatarioViews.DestinatarioConcretoDetallesConQr.class)
    public ResponseEntity<DestinatarioCajaActualizadaDto> asignarDestinatarioACaja(@PathVariable Long id, @PathVariable Long idDestinataro) {
        Optional<Caja> optC = servCaja.findById(id);
        Optional<Destinatario> optD = servDest.findById(idDestinataro);
        if(optC.isPresent()){
            if (optD.isPresent()){
                Destinatario d = optD.get();
                servCaja.asignarCaja(optC.get().getId(), d);
                Caja c = servCaja.findById(id).get();
                return ResponseEntity.ok(mapperDest.toDestinatarioCajaActualizadaDto(d, c));
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.save(ca));
    }

    @Operation(description = "Borra una caja y la lista de alimentos que contiene")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Caja borrada satisfactoriamente",
                    content = {@Content})
    })
    @DeleteMapping("/{id1}/tipo/{idTipoAlim}")
    public ResponseEntity<?> deleteCaja(@PathVariable Long id1, @PathVariable Long idTipoAlim){
        Optional<Caja> caja = cajaService.findById(id1);

        if(caja.isPresent()){
            Caja c = caja.get();

            Optional<TipoAlimento> tipoAlimentoOptional = repoTipoAli.findById(idTipoAlim);

            if(tipoAlimentoOptional.isPresent()) {
                TipoAlimento ta = tipoAlimentoOptional.get();

                Tiene tiene = cajaService.getAlimentoEnCaja(ta, c);

                cajaService.deleteById(id1);

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
