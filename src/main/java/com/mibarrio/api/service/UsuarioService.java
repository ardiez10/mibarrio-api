package com.mibarrio.api.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mibarrio.api.dto.usuario.CreateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UpdateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UsuarioResponse;
import com.mibarrio.api.exception.ConflictException;
import com.mibarrio.api.exception.NotFoundException;
import com.mibarrio.api.model.EstadoUsuario;
import com.mibarrio.api.model.Rol;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.RolRepository;
import com.mibarrio.api.repository.UsuarioRepository;
import com.mibarrio.api.service.email.PlatformEmailService;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlatformEmailService platformEmailService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            PlatformEmailService platformEmailService) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.platformEmailService = platformEmailService;
    }

    public List<UsuarioResponse> getAll() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UsuarioResponse getById(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    public List<UsuarioResponse> getPendientes() {
        return usuarioRepository.findByEstadoAndActivoTrue(EstadoUsuario.PENDIENTE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UsuarioResponse create(CreateUsuarioRequest request) {

        usuarioRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new ConflictException("Ya existe un usuario con ese email");
                });

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActivo(true);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse update(Long id, UpdateUsuarioRequest request) {

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setRol(rol);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    public void delete(Long id) {

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuario.setActivo(false);

        platformEmailService.sendComercioEventEmail(
                usuario,
                "Cuenta desactivada",
                "Tu cuenta ha sido desactivada por administracion."
        );

        usuarioRepository.save(usuario);
    }

    public UsuarioResponse approve(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuario.setEstado(EstadoUsuario.ACTIVO);
        Usuario savedUser = usuarioRepository.save(usuario);

        platformEmailService.sendApprovalEmail(savedUser);

        return mapToResponse(savedUser);
    }

    public UsuarioResponse reject(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuario.setEstado(EstadoUsuario.RECHAZADO);
        usuario.setActivo(false);
        Usuario savedUser = usuarioRepository.save(usuario);

        platformEmailService.sendRejectionEmail(savedUser);

        return mapToResponse(savedUser);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());

        if (usuario.getRol() != null) {
            response.setRolId(usuario.getRol().getId());
            response.setRolNombre(usuario.getRol().getNombre());
        }

        response.setCreatedAt(usuario.getCreatedAt());
        response.setUpdatedAt(usuario.getUpdatedAt());
        EstadoUsuario estado = usuario.getEstado();
        if (estado == null) {
            estado = Boolean.TRUE.equals(usuario.getActivo()) ? EstadoUsuario.ACTIVO : EstadoUsuario.RECHAZADO;
        }

        response.setEstado(estado.name());

        return response;
    }
}
