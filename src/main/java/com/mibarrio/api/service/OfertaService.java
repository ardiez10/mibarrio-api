package com.mibarrio.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mibarrio.api.dto.oferta.CreateOfertaRequest;
import com.mibarrio.api.dto.oferta.OfertaResponse;
import com.mibarrio.api.dto.oferta.UpdateOfertaRequest;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Oferta;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.OfertaRepository;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ComercioRepository comercioRepository;

    private final AuthorizationService authorizationService;

    public OfertaService(
            OfertaRepository ofertaRepository,
            ComercioRepository comercioRepository,
            AuthorizationService authorizationService) {

        this.ofertaRepository = ofertaRepository;
        this.comercioRepository = comercioRepository;
        this.authorizationService = authorizationService;
    }

    public List<OfertaResponse> getAll() {
        return ofertaRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OfertaResponse getById(Long id) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        return mapToResponse(oferta);
    }

    public List<OfertaResponse> getByComercioId(Long comercioId) {
        return ofertaRepository.findByComercioIdAndActivoTrue(comercioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OfertaResponse create(Long comercioId, CreateOfertaRequest request) {
        Comercio comercio = comercioRepository.findById(comercioId)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new RuntimeException("No tienes permisos para crear ofertas en este comercio");
        }

        Oferta oferta = new Oferta();
        oferta.setTitulo(request.getTitulo());
        oferta.setDescripcion(request.getDescripcion());
        oferta.setActivo(true);
        oferta.setComercio(comercio);

        return mapToResponse(ofertaRepository.save(oferta));
    }

    public OfertaResponse update(Long id, UpdateOfertaRequest request) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        if (!authorizationService.canManageComercio(oferta.getComercio())) {
            throw new RuntimeException("No tienes permisos para modificar esta oferta");
        }

        oferta.setTitulo(request.getTitulo());
        oferta.setDescripcion(request.getDescripcion());

        return mapToResponse(ofertaRepository.save(oferta));
    }

    public void delete(Long id) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        if (!authorizationService.canManageComercio(oferta.getComercio())) {
            throw new RuntimeException("No tienes permisos para eliminar esta oferta");
        }

        oferta.setActivo(false);
        ofertaRepository.save(oferta);
    }

    private OfertaResponse mapToResponse(Oferta oferta) {
        OfertaResponse response = new OfertaResponse();

        response.setId(oferta.getId());
        response.setTitulo(oferta.getTitulo());
        response.setDescripcion(oferta.getDescripcion());

        if (oferta.getComercio() != null) {
            response.setComercioId(oferta.getComercio().getId());
            response.setComercioNombre(oferta.getComercio().getNombre());
        }

        response.setCreatedAt(oferta.getCreatedAt());
        response.setUpdatedAt(oferta.getUpdatedAt());

        return response;
    }
}