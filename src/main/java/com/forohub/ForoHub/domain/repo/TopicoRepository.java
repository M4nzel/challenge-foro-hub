package com.forohub.ForoHub.domain.repo;

import com.forohub.ForoHub.domain.Topico;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);

    @Query("""
         select t from Topico t
         where (:curso is null or t.curso.nombre = :curso)
           and (:anio is null or function('year', t.fechaCreacion) = :anio)
         """)
    Page<Topico> buscarFiltrado(@Param("curso") String curso,
                                @Param("anio") Integer anio,
                                Pageable pageable);
}
