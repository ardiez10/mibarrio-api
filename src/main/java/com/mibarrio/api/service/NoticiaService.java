package com.mibarrio.api.service;

import com.mibarrio.api.exception.NotFoundException;
import com.mibarrio.api.dto.noticia.CreateNoticiaRequest;
import com.mibarrio.api.dto.noticia.NoticiaResponse;
import com.mibarrio.api.dto.noticia.UpdateNoticiaRequest;
import com.mibarrio.api.model.Noticia;
import com.mibarrio.api.repository.NoticiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;

    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public List<NoticiaResponse> getAll() {
        return noticiaRepository.findByActivoTrueOrderByFechaPublicacionDescCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<NoticiaResponse> getAllForAdmin() {
        return noticiaRepository.findAllByOrderByFechaPublicacionDescCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public NoticiaResponse getById(Long id) {
        Noticia noticia = noticiaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));

        return mapToResponse(noticia);
    }

    public NoticiaResponse create(CreateNoticiaRequest request) {
        Noticia noticia = findExistingByUrl(request.getUrl()).orElseGet(Noticia::new);
        boolean nueva = noticia.getId() == null;

        applyRequest(noticia, request.getTitulo(), request.getDescripcion(), request.getFuente(),
                request.getUrl(), request.getImagenUrl(), request.getFechaPublicacion());
        if (nueva) {
            noticia.setActivo(true);
        }

        return mapToResponse(noticiaRepository.save(noticia));
    }

    public NoticiaResponse update(Long id, UpdateNoticiaRequest request) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));

        applyRequest(noticia, request.getTitulo(), request.getDescripcion(), request.getFuente(),
                request.getUrl(), request.getImagenUrl(), request.getFechaPublicacion());

        return mapToResponse(noticiaRepository.save(noticia));
    }

    public void delete(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));

        noticia.setActivo(false);
        noticiaRepository.save(noticia);
    }

    public NoticiaResponse activate(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));

        noticia.setActivo(true);

        return mapToResponse(noticiaRepository.save(noticia));
    }

    private java.util.Optional<Noticia> findExistingByUrl(String url) {
        if (!StringUtils.hasText(url)) {
            return java.util.Optional.empty();
        }

        return noticiaRepository.findFirstByUrlOrderByIdDesc(url.trim());
    }

    private void applyRequest(Noticia noticia, String titulo, String descripcion, String fuente,
            String url, String imagenUrl, java.time.LocalDate fechaPublicacion) {
        noticia.setTitulo(titulo);
        noticia.setDescripcion(descripcion);
        noticia.setFuente(fuente);
        noticia.setUrl(StringUtils.hasText(url) ? url.trim() : null);
        noticia.setImagenUrl(StringUtils.hasText(imagenUrl) ? imagenUrl.trim() : null);
        noticia.setFechaPublicacion(fechaPublicacion);
    }

    private NoticiaResponse mapToResponse(Noticia noticia) {
        NoticiaResponse response = new NoticiaResponse();

        response.setId(noticia.getId());
        response.setTitulo(noticia.getTitulo());
        response.setDescripcion(noticia.getDescripcion());
        response.setFuente(noticia.getFuente());
        response.setUrl(noticia.getUrl());
        response.setImagenUrl(noticia.getImagenUrl());
        response.setFechaPublicacion(noticia.getFechaPublicacion());
        response.setActivo(noticia.getActivo());
        response.setCreatedAt(noticia.getCreatedAt());
        response.setUpdatedAt(noticia.getUpdatedAt());

        return response;
    }
}
