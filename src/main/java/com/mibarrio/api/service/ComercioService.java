package com.mibarrio.api.service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mibarrio.api.dto.comercio.ComercioResponse;
import com.mibarrio.api.dto.comercio.CreateComercioRequest;
import com.mibarrio.api.dto.comercio.UpdateComercioRequest;
import com.mibarrio.api.dto.horario.HorarioResponse;
import com.mibarrio.api.exception.ForbiddenException;
import com.mibarrio.api.exception.NotFoundException;
import com.mibarrio.api.model.Categoria;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.EstadoComercio;
import com.mibarrio.api.model.EstadoUsuario;
import com.mibarrio.api.model.Horario;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.CategoriaRepository;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.HorarioRepository;
import com.mibarrio.api.repository.UsuarioRepository;
import com.mibarrio.api.service.email.PlatformEmailService;

@Service
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final CategoriaRepository categoriaRepository;
    private final HorarioRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;

    private final AuthorizationService authorizationService;
    private final PlatformEmailService platformEmailService;

    public ComercioService(
            ComercioRepository comercioRepository,
            CategoriaRepository categoriaRepository,
            HorarioRepository horarioRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authorizationService,
            PlatformEmailService platformEmailService) {

        this.comercioRepository = comercioRepository;
        this.categoriaRepository = categoriaRepository;
        this.horarioRepository = horarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
        this.platformEmailService = platformEmailService;
    }

    public List<ComercioResponse> getAll() {
        return comercioRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ComercioResponse> getAllForAdmin() {
        return comercioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ComercioResponse> getMine() {
        Usuario currentUser = authorizationService.getCurrentUser();

        return comercioRepository.findByUsuarioId(currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ComercioResponse> getPendientes() {
        return comercioRepository.findByEstado(EstadoComercio.PENDIENTE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ComercioResponse getById(Long id) {
        Comercio comercio = comercioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        return mapToResponse(comercio);
    }

    public ComercioResponse create(CreateComercioRequest request) {
        Comercio comercio = new Comercio();

        comercio.setNombre(request.getNombre());
        comercio.setDireccion(request.getDireccion());
        comercio.setTelefono(request.getTelefono());
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        comercio.setCategoria(categoria);

        Usuario currentUser = authorizationService.getCurrentUserOrThrowIfNotAdminManaging(request.getUsuarioId());
        boolean admin = "ADMIN".equals(currentUser.getRol().getNombre());

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(request.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        comercio.setUsuario(usuario);
        comercio.setActivo(admin);
        comercio.setEstado(admin ? EstadoComercio.ACTIVO : EstadoComercio.PENDIENTE);

        Comercio savedComercio = comercioRepository.save(comercio);

        platformEmailService.sendComercioEventEmail(
                usuario,
                admin ? "Comercio creado" : "Solicitud de comercio recibida",
                admin
                        ? "Se ha creado o vinculado el comercio '%s' en tu cuenta.".formatted(savedComercio.getNombre())
                        : "Hemos recibido la solicitud para añadir el comercio '%s' a tu cuenta.".formatted(savedComercio.getNombre())
        );

        if (!admin) {
            platformEmailService.notifyAdminsOfPendingComercio(savedComercio);
        }

        return mapToResponse(savedComercio);
    }

    public ComercioResponse update(Long id, UpdateComercioRequest request) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new ForbiddenException("No tienes permisos para modificar este comercio");
        }

        comercio.setNombre(request.getNombre());
        comercio.setDireccion(request.getDireccion());
        comercio.setTelefono(request.getTelefono());

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        comercio.setCategoria(categoria);

        if (authorizationService.isAdmin()) {
            Usuario usuario = usuarioRepository.findByIdAndActivoTrue(request.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

            comercio.setUsuario(usuario);
        }

        Comercio updatedComercio = comercioRepository.save(comercio);

        if (updatedComercio.getUsuario() != null) {
            platformEmailService.sendComercioEventEmail(
                    updatedComercio.getUsuario(),
                    "Datos del comercio actualizados",
                    "Se han actualizado los datos del comercio '%s'.".formatted(updatedComercio.getNombre())
            );
        }

        return mapToResponse(updatedComercio);
    }

    public void delete(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new ForbiddenException("No tienes permisos para eliminar este comercio");
        }

        comercio.setActivo(false);
        comercio.setEstado(EstadoComercio.RECHAZADO);
        comercioRepository.save(comercio);

        if (comercio.getUsuario() != null) {
            platformEmailService.sendComercioEventEmail(
                    comercio.getUsuario(),
                    "Comercio desactivado",
                    "El comercio '%s' ha sido desactivado.".formatted(comercio.getNombre())
            );
        }
    }

    public ComercioResponse approve(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        comercio.setActivo(true);
        comercio.setEstado(EstadoComercio.ACTIVO);

        Usuario usuario = comercio.getUsuario();
        if (usuario != null && usuario.getEstado() == EstadoUsuario.PENDIENTE) {
            usuario.setEstado(EstadoUsuario.ACTIVO);
            usuarioRepository.save(usuario);
        }

        Comercio savedComercio = comercioRepository.save(comercio);

        if (usuario != null) {
            platformEmailService.sendApprovalEmail(usuario);
        }

        return mapToResponse(savedComercio);
    }

    public ComercioResponse reject(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comercio no encontrado"));

        comercio.setActivo(false);
        comercio.setEstado(EstadoComercio.RECHAZADO);

        Usuario usuario = comercio.getUsuario();
        Comercio savedComercio = comercioRepository.save(comercio);

        if (usuario != null) {
            boolean hasOtherPending = comercioRepository
                    .findByUsuarioIdAndEstado(usuario.getId(), EstadoComercio.PENDIENTE)
                    .stream()
                    .anyMatch(other -> !other.getId().equals(comercio.getId()));

            if (usuario.getEstado() == EstadoUsuario.PENDIENTE && !hasOtherPending) {
                usuario.setEstado(EstadoUsuario.RECHAZADO);
                usuario.setActivo(false);
                usuarioRepository.save(usuario);
            }

            platformEmailService.sendRejectionEmail(usuario);
        }

        return mapToResponse(savedComercio);
    }

    private ComercioResponse mapToResponse(Comercio comercio) {
        ComercioResponse response = new ComercioResponse();

        response.setId(comercio.getId());
        response.setNombre(comercio.getNombre());
        response.setDireccion(comercio.getDireccion());
        response.setTelefono(comercio.getTelefono());

        EstadoComercio estado = comercio.getEstado();
        if (estado == null) {
            estado = Boolean.TRUE.equals(comercio.getActivo()) ? EstadoComercio.ACTIVO : EstadoComercio.RECHAZADO;
        }

        response.setActivo(estado == EstadoComercio.ACTIVO && Boolean.TRUE.equals(comercio.getActivo()));
        response.setEstado(estado.name());
        response.setImagenUrl(comercio.getImagenUrl());
        response.setHorarios(horarioRepository.findByComercioIdAndActivoTrue(comercio.getId())
                .stream()
                .sorted(Comparator
                        .comparing(Horario::getDiaSemana, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(Horario::getHoraApertura, Comparator.nullsLast(LocalTime::compareTo)))
                .map(this::mapHorarioToResponse)
                .toList());

        if (comercio.getCategoria() != null) {
            response.setCategoriaId(comercio.getCategoria().getId());
            response.setCategoria(comercio.getCategoria().getNombre());
        }

        response.setCreatedAt(comercio.getCreatedAt());
        response.setUpdatedAt(comercio.getUpdatedAt());

        if (comercio.getUsuario() != null) {
            response.setUsuarioId(comercio.getUsuario().getId());
            response.setUsuarioNombre(comercio.getUsuario().getNombre());
            response.setUsuarioEmail(comercio.getUsuario().getEmail());
        }

        return response;
    }

    private HorarioResponse mapHorarioToResponse(Horario horario) {
        HorarioResponse response = new HorarioResponse();

        response.setId(horario.getId());
        response.setDiaSemana(horario.getDiaSemana());
        response.setHoraApertura(horario.getHoraApertura());
        response.setHoraCierre(horario.getHoraCierre());

        if (horario.getComercio() != null) {
            response.setComercioId(horario.getComercio().getId());
            response.setComercioNombre(horario.getComercio().getNombre());
        }

        return response;
    }
}
