package com.mibarrio.api.controller;

import com.mibarrio.api.model.Oferta;
import com.mibarrio.api.service.OfertaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping
    public List<Oferta> getAll() {
        return ofertaService.getAll();
    }

    @GetMapping("/{id}")
    public Oferta getById(@PathVariable Long id) {
        return ofertaService.getById(id);
    }

    @GetMapping("/comercio/{comercioId}")
    public List<Oferta> getByComercioId(@PathVariable Long comercioId) {
        return ofertaService.getByComercioId(comercioId);
    }

    @PostMapping("/comercio/{comercioId}")
    public Oferta create(@PathVariable Long comercioId, @RequestBody Oferta oferta) {
        return ofertaService.create(comercioId, oferta);
    }

    @PutMapping("/{id}")
    public Oferta update(@PathVariable Long id, @RequestBody Oferta oferta) {
        return ofertaService.update(id, oferta);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ofertaService.delete(id);
    }
}