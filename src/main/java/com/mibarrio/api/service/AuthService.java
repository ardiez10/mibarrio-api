package com.mibarrio.api.service;

import com.mibarrio.api.dto.auth.LoginRequest;
import com.mibarrio.api.dto.auth.LoginResponse;
import com.mibarrio.api.dto.auth.SignupRequest;
import com.mibarrio.api.dto.auth.SignupResponse;
import com.mibarrio.api.exception.AccountPendingApprovalException;
import com.mibarrio.api.exception.ConflictException;
import com.mibarrio.api.exception.NotFoundException;
import com.mibarrio.api.exception.UnauthorizedException;
import com.mibarrio.api.model.EstadoUsuario;
import com.mibarrio.api.model.Categoria;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.EstadoComercio;
import com.mibarrio.api.model.Rol;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.CategoriaRepository;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.RolRepository;
import com.mibarrio.api.repository.UsuarioRepository;
import com.mibarrio.api.service.email.PlatformEmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final CategoriaRepository categoriaRepository;
    private final ComercioRepository comercioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PlatformEmailService platformEmailService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            CategoriaRepository categoriaRepository,
            ComercioRepository comercioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            PlatformEmailService platformEmailService) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.categoriaRepository = categoriaRepository;
        this.comercioRepository = comercioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.platformEmailService = platformEmailService;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmailAndActivoTrue(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword());

        if (!passwordMatches) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        EstadoUsuario estado = usuario.getEstado();
        if (estado == null) {
            estado = EstadoUsuario.ACTIVO;
        }

        if (estado == EstadoUsuario.PENDIENTE) {
            throw new AccountPendingApprovalException("Tu cuenta esta pendiente de aprobacion");
        }

        if (estado == EstadoUsuario.RECHAZADO) {
            throw new UnauthorizedException("Tu cuenta ha sido rechazada");
        }

        String token = jwtService.generateToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getId(),
                usuario.getRol().getNombre()
        );
    }

    public SignupResponse signup(SignupRequest request) {

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("La categoría no existe"));

        Usuario savedUser = usuarioRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    if (!Boolean.TRUE.equals(existingUser.getActivo())) {
                        throw new ConflictException("Ya existe una cuenta desactivada con ese email");
                    }

                    if (!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
                        throw new UnauthorizedException("Ese email ya existe. Inicia sesion o usa su contraseña actual");
                    }

                    if (!"COMERCIO".equals(existingUser.getRol().getNombre())) {
                        throw new ConflictException("Ese email ya pertenece a una cuenta que no puede gestionar comercios");
                    }

                    return existingUser;
                })
                .orElseGet(() -> {
                    Rol rol = rolRepository.findByNombre("COMERCIO")
                            .orElseThrow(() -> new NotFoundException("El rol COMERCIO no existe"));

                    Usuario usuario = new Usuario();
                    usuario.setNombre(request.getNombre());
                    usuario.setEmail(request.getEmail());
                    usuario.setPassword(passwordEncoder.encode(request.getPassword()));
                    usuario.setActivo(true);
                    usuario.setRol(rol);
                    usuario.setEstado(EstadoUsuario.PENDIENTE);

                    return usuarioRepository.save(usuario);
                });

        Comercio comercio = new Comercio();
        comercio.setNombre(request.getComercioNombre());
        comercio.setDireccion(request.getComercioDireccion());
        comercio.setTelefono(request.getComercioTelefono());
        comercio.setCategoria(categoria);
        comercio.setUsuario(savedUser);
        comercio.setActivo(false);
        comercio.setEstado(EstadoComercio.PENDIENTE);
        Comercio savedComercio = comercioRepository.save(comercio);

        platformEmailService.sendSignupReceivedEmail(savedUser);
        platformEmailService.notifyAdminsOfPendingComercio(savedComercio);

        return new SignupResponse(
                savedUser.getEmail(),
                savedUser.getEstado().name(),
                "Tu solicitud de comercio se ha registrado y esta pendiente de aprobacion"
        );
    }
}
