package com.grupocinco.kilosapi.Controller;

import com.grupocinco.kilosapi.dto.tipoAlimento.TipoAlimentoDto;
import com.grupocinco.kilosapi.service.TipoAlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoAlimento/")
public class TipoAlimentoController {

    @Autowired
    private TipoAlimentoService service;

    @GetMapping()
    public ResponseEntity<List<TipoAlimentoDto>> getListaTipoAlimentos(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAlimentoDto> getTipoAlimentoById(){
        return null;
    }

    @PostMapping()
     public ResponseEntity<TipoAlimentoDto> addTipoAlimento(){
        return null;
     }

    @PutMapping("/{id}")
    public ResponseEntity<TipoAlimentoDto> editTipoAlimento(){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTipoAlimento(){
        return null;
    }
}
