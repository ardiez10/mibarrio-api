package com.mibarrio.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mibarrio.api.dto.oferta.CreateOfertaRequest;
import com.mibarrio.api.dto.oferta.OfertaResponse;
import com.mibarrio.api.dto.oferta.UpdateOfertaRequest;
import com.mibarrio.api.exception.ForbiddenException;
import com.mibarrio.api.exception.NotFoundException;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Oferta;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.OfertaRepository;
import com.mibarrio.api.service.email.PlatformEmailService;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ComercioRepository comercioRepository;

    private final AuthorizationService authorizationService;
    private final PlatformEmailService platformEmailService;

    public OfertaService(
            OfertaRepository ofertaRepository,
            ComercioRepository comercioRepository,
            AuthorizationService authorizationService,
            PlatformEmailService platformEmailService) {

        this.ofertaRepository = ofertaRepository;
        this.comercioRepository = comercioRepository;
        this.authorizationService = authorizationService;
        this.platformEmailService = platformEmailService;
    }

    public List<OfertaResponse> getAll() {
        return ofertaRepository.findPublicActive()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OfertaResponse getById(Long id) {
        Oferta oferta = ofertaRepository.findPublicActiveById(id)
                .orElseThrow(() -> new NotFoundException("Oferta no encontrada"));

        return mapToResponse(oferta);
    }

    public List<OfertaResponse> getByComercioId(Long comercioId) {
        return ofertaRepository.findPublicActiveByComercioId(comercioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OfertaResponse create(Long comercioId, CreateOfertaRequest request) {
        Comercio comercio = comercioRepository.findById(comercioId)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new ForbiddenException("No tienes permisos para crear ofertas en este comercio");
        }

        Oferta oferta = new Oferta();
        oferta.setTitulo(request.getTitulo());
        oferta.setDescripcion(request.getDescripcion());
        oferta.setActivo(true);
        oferta.setFechaFin(request.getFechaFin());
        oferta.setComercio(comercio);

        Oferta savedOferta = ofertaRepository.save(oferta);

        if (comercio.getUsuario() != null) {
            platformEmailService.sendComercioEventEmail(
                    comercio.getUsuario(),
                    "Oferta publicada",
                    "Se ha publicado la oferta '%s' para tu comercio.".formatted(savedOferta.getTitulo())
            );
        }

        return mapToResponse(savedOferta);
    }

    public OfertaResponse update(Long id, UpdateOfertaRequest request) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Oferta no encontrada"));

        if (!authorizationService.canManageComercio(oferta.getComercio())) {
            throw new ForbiddenException("No tienes permisos para modificar esta oferta");
        }

        oferta.setTitulo(request.getTitulo());
        oferta.setDescripcion(request.getDescripcion());
        oferta.setFechaFin(request.getFechaFin());

        Oferta updatedOferta = ofertaRepository.save(oferta);

        if (updatedOferta.getComercio() != null && updatedOferta.getComercio().getUsuario() != null) {
            platformEmailService.sendComercioEventEmail(
                    updatedOferta.getComercio().getUsuario(),
                    "Oferta actualizada",
                    "Se ha actualizado la oferta '%s'.".formatted(updatedOferta.getTitulo())
            );
        }

        return mapToResponse(updatedOferta);
    }

    public void delete(Long id) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Oferta no encontrada"));

        if (!authorizationService.canManageComercio(oferta.getComercio())) {
            throw new ForbiddenException("No tienes permisos para eliminar esta oferta");
        }

        oferta.setActivo(false);
        ofertaRepository.save(oferta);

        if (oferta.getComercio() != null && oferta.getComercio().getUsuario() != null) {
            platformEmailService.sendComercioEventEmail(
                    oferta.getComercio().getUsuario(),
                    "Oferta desactivada",
                    "La oferta '%s' ha sido desactivada.".formatted(oferta.getTitulo())
            );
        }
    }

    private OfertaResponse mapToResponse(Oferta oferta) {
        OfertaResponse response = new OfertaResponse();

        response.setId(oferta.getId());
        response.setTitulo(oferta.getTitulo());
        response.setDescripcion(oferta.getDescripcion());
        response.setActivo(oferta.getActivo());
        response.setFechaFin(oferta.getFechaFin());

        if (oferta.getComercio() != null) {
            response.setComercioId(oferta.getComercio().getId());
            response.setComercioNombre(oferta.getComercio().getNombre());

            if (oferta.getComercio().getCategoria() != null) {
                response.setCategoria(oferta.getComercio().getCategoria().getNombre());
            }
        }

        response.setCreatedAt(oferta.getCreatedAt());
        response.setUpdatedAt(oferta.getUpdatedAt());

        return response;
    }
}
