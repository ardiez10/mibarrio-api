package com.mibarrio.api.service;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.repository.ComercioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComercioService {

    private final ComercioRepository comercioRepository;

    public ComercioService(ComercioRepository comercioRepository) {
        this.comercioRepository = comercioRepository;
    }

    public List<Comercio> getAll() {
        return comercioRepository.findAll();
    }

    public Comercio create(Comercio comercio) {
        return comercioRepository.save(comercio);
    }
}