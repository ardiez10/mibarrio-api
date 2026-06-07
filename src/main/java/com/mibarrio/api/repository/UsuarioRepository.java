package com.mibarrio.api.repository;

import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.model.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByIdAndActivoTrue(Long id);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndActivoTrue(String email);

    List<Usuario> findByEstadoAndActivoTrue(EstadoUsuario estado);

    List<Usuario> findByRolNombreAndActivoTrue(String nombreRol);
}
