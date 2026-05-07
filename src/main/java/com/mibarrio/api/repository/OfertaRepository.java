package com.mibarrio.api.repository;

import com.mibarrio.api.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByActivoTrue();

    List<Oferta> findByComercioIdAndActivoTrue(Long comercioId);
}