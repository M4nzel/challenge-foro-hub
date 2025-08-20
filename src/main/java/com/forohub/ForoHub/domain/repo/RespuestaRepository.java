package com.forohub.ForoHub.domain.repo;

import com.forohub.ForoHub.domain.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    @Query("select count(r) > 0 from Respuesta r where r.topico.id = :topicoId and r.solucion = true")
    boolean existeSolucionEnTopico(Long topicoId);
}
