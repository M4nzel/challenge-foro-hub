package com.forohub.ForoHub.security;

import com.forohub.ForoHub.domain.Usuario;
import com.forohub.ForoHub.domain.repo.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarios;

    public JpaUserDetailsService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarios.findByCorreoElectronico(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> auths = u.getPerfiles().stream()
                .map(p -> new SimpleGrantedAuthority(p.getNombre()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                u.getCorreoElectronico(), u.getContrasena(), auths
        );
    }
}
