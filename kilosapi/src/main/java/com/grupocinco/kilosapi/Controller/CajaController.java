package com.grupocinco.kilosapi.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Caja;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.view.CajaViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/caja")
public class CajaController {
    @Autowired
    private CajaRepository repoCaja;

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
    public ResponseEntity<List<Caja>> getCajas(){
        List<Caja> listaCajas = repoCaja.findAll();
        if(listaCajas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listaCajas);
    }
}
