package com.mibarrio.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.EstadoComercio;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {
    List<Comercio> findByActivoTrue();
    Optional<Comercio> findByIdAndActivoTrue(Long id);
    List<Comercio> findByEstado(EstadoComercio estado);
    List<Comercio> findByUsuarioIdAndEstado(Long usuarioId, EstadoComercio estado);
}
