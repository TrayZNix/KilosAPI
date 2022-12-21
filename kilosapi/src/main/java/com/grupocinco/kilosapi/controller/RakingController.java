package com.grupocinco.kilosapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioDto;
import com.grupocinco.kilosapi.dto.destinatario.DestinatarioMapper;
import com.grupocinco.kilosapi.dto.view.DestinatarioViews;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.service.CajaService;
import com.grupocinco.kilosapi.service.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/ranking")
@Tag(name = "Ranking", description = "Controlador que manejará (Por ahora...) la peticion GET ranking")
public class RakingController {

    @Autowired
    private DestinatarioService servDest;
    @Autowired
    private CajaService servCaja;
    @Autowired
    private DestinatarioMapper mapperDest;

    @Operation(summary = "Devuelve un ranking según los datos actuales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El ranking se generó y devolvió correctamente",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                           
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna clase",
                    content = {@Content})
    })
    @GetMapping()
    @JsonView(DestinatarioViews.DestinatarioList.class)
    public ResponseEntity<List<DestinatarioDto>> getRanking(){

//        return ResponseEntity.ok();
    }
}
