package com.mibarrio.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mibarrio.api.model.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    List<Noticia> findByActivoTrue();
    Optional<Noticia> findByIdAndActivoTrue(Long id);
}