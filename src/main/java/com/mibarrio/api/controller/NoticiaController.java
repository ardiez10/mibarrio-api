package com.mibarrio.api.controller;

import com.mibarrio.api.dto.noticia.CreateNoticiaRequest;
import com.mibarrio.api.dto.noticia.NoticiaResponse;
import com.mibarrio.api.dto.noticia.UpdateNoticiaRequest;
import com.mibarrio.api.service.NoticiaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping
    public List<NoticiaResponse> getAll() {
        return noticiaService.getAll();
    }

    @GetMapping("/{id}")
    public NoticiaResponse getById(@PathVariable Long id) {
        return noticiaService.getById(id);
    }

    @PostMapping
    public NoticiaResponse create(@Valid @RequestBody CreateNoticiaRequest request) {
        return noticiaService.create(request);
    }

    @PutMapping("/{id}")
    public NoticiaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNoticiaRequest request) {
        return noticiaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noticiaService.delete(id);
    }
}