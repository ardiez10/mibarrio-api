package com.mibarrio.api.repository;

import com.mibarrio.api.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    List<Noticia> findByActivoTrue();
}