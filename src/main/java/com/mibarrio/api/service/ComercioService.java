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
        return comercioRepository.findByActivoTrue(); //solo sacamos los activos
    }

    public Comercio create(Comercio comercio) {
        return comercioRepository.save(comercio);
    }

    public Comercio getById(Long id) {
        return comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
    }

    public Comercio update(Long id, Comercio updatedComercio) {

        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        comercio.setNombre(updatedComercio.getNombre());
        comercio.setDireccion(updatedComercio.getDireccion());
        comercio.setTelefono(updatedComercio.getTelefono());

        return comercioRepository.save(comercio);
    }

    public void delete(Long id) { //borrado lógico

        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        comercio.setActivo(false);

        comercioRepository.save(comercio);
    }
}