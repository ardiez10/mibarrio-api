package com.mibarrio.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mibarrio.api.model.Comercio;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {
    List<Comercio> findByActivoTrue();
    Optional<Comercio> findByIdAndActivoTrue(Long id);
}
