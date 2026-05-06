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

    @GetMapping("/{id}")
    public Comercio getById(@PathVariable Long id) {
        return comercioService.getById(id);
    }

    @PutMapping("/{id}")
    public Comercio update(
            @PathVariable Long id,
            @RequestBody Comercio comercio) {

        return comercioService.update(id, comercio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        comercioService.delete(id);
    }
}