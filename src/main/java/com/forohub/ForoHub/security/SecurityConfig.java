package com.forohub.ForoHub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService jpaUserDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService jpaUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Proveedor JPA (BD) + (opcional) usuario en memoria para pruebas rápidas.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config,
                                                PasswordEncoder encoder) throws Exception {
        DaoAuthenticationProvider jpaProvider = new DaoAuthenticationProvider();
        jpaProvider.setUserDetailsService(jpaUserDetailsService);
        jpaProvider.setPasswordEncoder(encoder);

        // **Opcional**: usuario en memoria (útil si aún no has sembrado la BD).
        UserDetails demo = User.withUsername("demo@forohub.local")
                .password(encoder.encode("123456"))
                .roles("USER","ADMIN")
                .build();
        InMemoryUserDetailsManager inMemory = new InMemoryUserDetailsManager(demo);

        return new ProviderManager(jpaProvider, new DaoAuthenticationProvider() {{
            setUserDetailsService(inMemory);
            setPasswordEncoder(encoder);
        }});
    }
}
