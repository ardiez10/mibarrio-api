package com.mibarrio.api.service;

import com.mibarrio.api.dto.categoria.CategoriaResponse;
import com.mibarrio.api.dto.categoria.CreateCategoriaRequest;
import com.mibarrio.api.dto.categoria.UpdateCategoriaRequest;
import com.mibarrio.api.model.Categoria;
import com.mibarrio.api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> getAll() {
        return categoriaRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoriaResponse getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return mapToResponse(categoria);
    }

    public CategoriaResponse create(CreateCategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setActivo(true);

        return mapToResponse(categoriaRepository.save(categoria));
    }

    public CategoriaResponse update(Long id, UpdateCategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(request.getNombre());

        return mapToResponse(categoriaRepository.save(categoria));
    }

    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }

    private CategoriaResponse mapToResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId());
        response.setNombre(categoria.getNombre());
        return response;
    }
}