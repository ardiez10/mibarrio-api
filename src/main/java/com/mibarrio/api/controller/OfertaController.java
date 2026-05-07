package com.mibarrio.api.controller;

import com.mibarrio.api.dto.oferta.CreateOfertaRequest;
import com.mibarrio.api.dto.oferta.OfertaResponse;
import com.mibarrio.api.dto.oferta.UpdateOfertaRequest;
import com.mibarrio.api.service.OfertaService;

import jakarta.validation.Valid;

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
    public List<OfertaResponse> getAll() {
        return ofertaService.getAll();
    }

    @GetMapping("/{id}")
    public OfertaResponse getById(@PathVariable Long id) {
        return ofertaService.getById(id);
    }

    @GetMapping("/comercio/{comercioId}")
    public List<OfertaResponse> getByComercioId(@PathVariable Long comercioId) {
        return ofertaService.getByComercioId(comercioId);
    }

    @PostMapping("/comercio/{comercioId}")
    public OfertaResponse create(
            @PathVariable Long comercioId,
            @Valid @RequestBody CreateOfertaRequest request) {

        return ofertaService.create(comercioId, request);
    }

    @PutMapping("/{id}")
    public OfertaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOfertaRequest request) {

        return ofertaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ofertaService.delete(id);
    }
}