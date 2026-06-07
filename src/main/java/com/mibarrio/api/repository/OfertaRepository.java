package com.mibarrio.api.repository;

import com.mibarrio.api.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    @Query("""
            select o
            from Oferta o
            where o.activo = true
              and o.comercio.activo = true
              and (o.fechaFin is null or o.fechaFin >= current_date)
            """)
    List<Oferta> findPublicActive();

    @Query("""
            select o
            from Oferta o
            where o.id = :id
              and o.activo = true
              and o.comercio.activo = true
              and (o.fechaFin is null or o.fechaFin >= current_date)
            """)
    Optional<Oferta> findPublicActiveById(Long id);

    @Query("""
            select o
            from Oferta o
            where o.comercio.id = :comercioId
              and o.activo = true
              and o.comercio.activo = true
              and (o.fechaFin is null or o.fechaFin >= current_date)
            """)
    List<Oferta> findPublicActiveByComercioId(Long comercioId);
}
