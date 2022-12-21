package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.dto.ranking.RankingDto;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Aportacion;
import com.grupocinco.kilosapi.service.AportacionService;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.ClaseService;
import com.grupocinco.kilosapi.service.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("/ranking")
@Tag(name = "Ranking", description = "Controlador que manejará (Por ahora...) la peticion GET ranking")
public class RankingController {

    @Autowired
    private AportacionService servAport;
    @Autowired
    private ClaseService servClase;

    @Operation(summary = "Devuelve un ranking según los datos actuales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El ranking se generó y devolvió correctamente",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "posicion": "1º",
                                                     "id": 11,
                                                     "nombre": "Clase tal",
                                                     "cantidadAportaciones": 2,
                                                     "mediaKilosAportados": 11.67,
                                                     "kilosTotalesAportados": 35.0
                                                 },
                                                 {
                                                     "posicion": "2º",
                                                     "id": 12,
                                                     "nombre": "Clase tal 2",
                                                     "cantidadAportaciones": 1,
                                                     "mediaKilosAportados": 0.0,
                                                     "kilosTotalesAportados": 0.0
                                                 }
                                             ]
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna clase",
                    content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<List<RankingDto>> getRanking(){
        List<RankingDto> datosClase = servClase.getRankingClase();
        if(datosClase.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            for(RankingDto clase : datosClase){
                clase.setMediaKilosAportados((double)Math.round(servClase.getAvgKilos(clase.getId())*100)/100);
                clase.setKilosTotalesAportados(servClase.getSumKilos(clase.getId()));
            };
            Collections.sort(datosClase);
            int position = 1;
            for(RankingDto clase : datosClase){
                clase.setPosicion(String.valueOf(position)+'º');
                position += 1;
            }
            return ResponseEntity.ok(datosClase);
        }
    }
}
