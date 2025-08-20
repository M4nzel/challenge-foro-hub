package com.forohub.ForoHub.domain.repo;

import com.forohub.ForoHub.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNombre(String nombre);
}
