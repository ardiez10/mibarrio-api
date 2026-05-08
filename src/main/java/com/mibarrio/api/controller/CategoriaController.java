package com.mibarrio.api.controller;

import com.mibarrio.api.dto.categoria.CategoriaResponse;
import com.mibarrio.api.dto.categoria.CreateCategoriaRequest;
import com.mibarrio.api.dto.categoria.UpdateCategoriaRequest;
import com.mibarrio.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponse> getAll() {
        return categoriaService.getAll();
    }

    @GetMapping("/{id}")
    public CategoriaResponse getById(@PathVariable Long id) {
        return categoriaService.getById(id);
    }

    @PostMapping
    public CategoriaResponse create(@Valid @RequestBody CreateCategoriaRequest request) {
        return categoriaService.create(request);
    }

    @PutMapping("/{id}")
    public CategoriaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoriaRequest request) {
        return categoriaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoriaService.delete(id);
    }
}