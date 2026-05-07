package com.mibarrio.api.service;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Oferta;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.OfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ComercioRepository comercioRepository;

    public OfertaService(OfertaRepository ofertaRepository, ComercioRepository comercioRepository) {
        this.ofertaRepository = ofertaRepository;
        this.comercioRepository = comercioRepository;
    }

    public List<Oferta> getAll() {
        return ofertaRepository.findByActivoTrue();
    }

    public Oferta getById(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    }

    public List<Oferta> getByComercioId(Long comercioId) {
        return ofertaRepository.findByComercioIdAndActivoTrue(comercioId);
    }

    public Oferta create(Long comercioId, Oferta oferta) {
        Comercio comercio = comercioRepository.findById(comercioId)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        oferta.setComercio(comercio);
        oferta.setActivo(true);

        return ofertaRepository.save(oferta);
    }

    public Oferta update(Long id, Oferta updatedOferta) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        oferta.setTitulo(updatedOferta.getTitulo());
        oferta.setDescripcion(updatedOferta.getDescripcion());

        return ofertaRepository.save(oferta);
    }

    public void delete(Long id) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        oferta.setActivo(false);
        ofertaRepository.save(oferta);
    }
}