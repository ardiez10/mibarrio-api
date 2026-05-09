package com.mibarrio.api.service;

import com.mibarrio.api.dto.noticia.CreateNoticiaRequest;
import com.mibarrio.api.dto.noticia.NoticiaResponse;
import com.mibarrio.api.dto.noticia.UpdateNoticiaRequest;
import com.mibarrio.api.model.Noticia;
import com.mibarrio.api.repository.NoticiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;

    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public List<NoticiaResponse> getAll() {
        return noticiaRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public NoticiaResponse getById(Long id) {
        Noticia noticia = noticiaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        return mapToResponse(noticia);
    }

    public NoticiaResponse create(CreateNoticiaRequest request) {
        Noticia noticia = new Noticia();

        noticia.setTitulo(request.getTitulo());
        noticia.setDescripcion(request.getDescripcion());
        noticia.setFuente(request.getFuente());
        noticia.setUrl(request.getUrl());
        noticia.setFechaPublicacion(request.getFechaPublicacion());
        noticia.setActivo(true);

        return mapToResponse(noticiaRepository.save(noticia));
    }

    public NoticiaResponse update(Long id, UpdateNoticiaRequest request) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticia.setTitulo(request.getTitulo());
        noticia.setDescripcion(request.getDescripcion());
        noticia.setFuente(request.getFuente());
        noticia.setUrl(request.getUrl());
        noticia.setFechaPublicacion(request.getFechaPublicacion());

        return mapToResponse(noticiaRepository.save(noticia));
    }

    public void delete(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticia.setActivo(false);
        noticiaRepository.save(noticia);
    }

    private NoticiaResponse mapToResponse(Noticia noticia) {
        NoticiaResponse response = new NoticiaResponse();

        response.setId(noticia.getId());
        response.setTitulo(noticia.getTitulo());
        response.setDescripcion(noticia.getDescripcion());
        response.setFuente(noticia.getFuente());
        response.setUrl(noticia.getUrl());
        response.setFechaPublicacion(noticia.getFechaPublicacion());

        return response;
    }
}