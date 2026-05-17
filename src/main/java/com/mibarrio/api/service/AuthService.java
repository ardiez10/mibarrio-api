package com.mibarrio.api.service;

import com.mibarrio.api.dto.auth.LoginRequest;
import com.mibarrio.api.dto.auth.LoginResponse;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmailAndActivoTrue(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }
}