package com.mibarrio.api.controller;

import com.mibarrio.api.dto.comercio.ComercioResponse;
import com.mibarrio.api.dto.comercio.CreateComercioRequest;
import com.mibarrio.api.dto.comercio.UpdateComercioRequest;
import com.mibarrio.api.service.ComercioService;

import jakarta.validation.Valid;

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
    public List<ComercioResponse> getAll() {
        return comercioService.getAll();
    }

    @GetMapping("/{id}")
    public ComercioResponse getById(@PathVariable Long id) {
        return comercioService.getById(id);
    }

    @PostMapping
    public ComercioResponse create(@Valid @RequestBody CreateComercioRequest request) {
        return comercioService.create(request);
    }

    @PutMapping("/{id}")
    public ComercioResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComercioRequest request) {
        return comercioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        comercioService.delete(id);
    }
}