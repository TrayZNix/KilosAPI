package com.grupocinco.kilosapi.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.grupocinco.kilosapi.model.Destinatario;
import com.grupocinco.kilosapi.repository.CajaRepository;
import com.grupocinco.kilosapi.repository.DestinatarioRepository;
import com.grupocinco.kilosapi.view.DestinatarioViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/destinatario")
public class DestinatarioController {
    @Autowired
    private DestinatarioRepository repoDestinatarios;
    @Autowired

    private CajaRepository repoCaja;
    @GetMapping()
    @JsonView(DestinatarioViews.DestinatarioList.class)
    public ResponseEntity<List<Destinatario>> getListaDestinatarios(){
        List<Destinatario> listaDest = repoDestinatarios.findAll();
        System.out.println(listaDest);
        if(listaDest.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listaDest);
    }

    @GetMapping("/{id}")
    @JsonView(DestinatarioViews.DestinatarioConcreto.class)
    public ResponseEntity<Destinatario> getDestinatarioConcreto(@PathVariable Long id){
        return ResponseEntity.of(repoDestinatarios.findById(id));
    }

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
