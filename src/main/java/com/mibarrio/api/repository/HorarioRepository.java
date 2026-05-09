package com.mibarrio.api.repository;

import com.mibarrio.api.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByActivoTrue();

    Optional<Horario> findByIdAndActivoTrue(Long id);

    List<Horario> findByComercioIdAndActivoTrue(Long comercioId);
}