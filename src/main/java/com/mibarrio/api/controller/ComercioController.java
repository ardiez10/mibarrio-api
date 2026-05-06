package com.mibarrio.api.controller;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.service.ComercioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercios")
public class ComercioController {

    private final ComercioService comercioService;

    public ComercioController(ComercioService comercioService) {
        this.comercioService = comercioService;
    }

    @GetMapping
    public List<Comercio> getAll() {
        return comercioService.getAll();
    }

    @PostMapping
    public Comercio create(@RequestBody Comercio comercio) {
        return comercioService.create(comercio);
    }
}