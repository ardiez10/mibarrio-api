package com.mibarrio.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import com.mibarrio.api.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/auth/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/comercios/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/comercios/mios").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.GET, "/api/comercios/pendientes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/comercios/*/aprobar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/comercios/*/rechazar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/noticias/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/noticias/*/activar").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/comercios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ofertas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/noticias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/horarios/**").permitAll()

                        .requestMatchers("/api/roles/**").hasRole("ADMIN")
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/noticias/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/comercios/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.PUT, "/api/comercios/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/comercios/**").hasAnyRole("ADMIN", "COMERCIO")

                        .requestMatchers(HttpMethod.POST, "/api/ofertas/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.PUT, "/api/ofertas/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/ofertas/**").hasAnyRole("ADMIN", "COMERCIO")

                        .requestMatchers(HttpMethod.POST, "/api/horarios/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.PUT, "/api/horarios/**").hasAnyRole("ADMIN", "COMERCIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/horarios/**").hasAnyRole("ADMIN", "COMERCIO")
                        .anyRequest().authenticated())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
