package com.mibarrio.api.repository;

import com.mibarrio.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByIdAndActivoTrue(Long id);

    Optional<Usuario> findByEmail(String email);
}