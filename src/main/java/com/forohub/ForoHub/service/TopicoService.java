package com.forohub.ForoHub.service;

import com.forohub.ForoHub.api.dto.TopicoCreateDTO;
import com.forohub.ForoHub.api.dto.TopicoResponseDTO;
import com.forohub.ForoHub.domain.*;
import com.forohub.ForoHub.domain.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    private final TopicoRepository topicos;
    private final UsuarioRepository usuarios;
    private final CursoRepository cursos;

    public TopicoService(TopicoRepository topicos, UsuarioRepository usuarios, CursoRepository cursos) {
        this.topicos = topicos; this.usuarios = usuarios; this.cursos = cursos;
    }

    @Transactional
    public TopicoResponseDTO crear(TopicoCreateDTO dto) {
        if (topicos.existsByTituloAndMensaje(dto.getTitulo(), dto.getMensaje()))
            throw new IllegalArgumentException("Tópico duplicado (título + mensaje).");

        Usuario autor = usuarios.findById(dto.getAutorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor no existe"));
        Curso curso = cursos.findById(dto.getCursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no existe"));

        Topico t = new Topico();
        t.setTitulo(dto.getTitulo());
        t.setMensaje(dto.getMensaje());
        t.setFechaCreacion(LocalDateTime.now());
        t.setStatus(StatusTopico.ABIERTO);
        t.setAutor(autor);
        t.setCurso(curso);

        t = topicos.save(t);
        return map(t);
    }

    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> listar(String curso, Integer anio, Pageable pageable) {
        return topicos.buscarFiltrado(curso, anio, pageable).map(this::map);
    }

    @Transactional(readOnly = true)
    public TopicoResponseDTO detalle(Long id) {
        Topico t = topicos.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        return map(t);
    }

    @Transactional
    public TopicoResponseDTO actualizar(Long id, TopicoCreateDTO dto) {
        Topico t = topicos.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        if (topicos.existsByTituloAndMensajeAndIdNot(dto.getTitulo(), dto.getMensaje(), id))
            throw new IllegalArgumentException("Otro tópico ya tiene ese título+mensaje.");

        Usuario autor = usuarios.findById(dto.getAutorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor no existe"));
        Curso curso = cursos.findById(dto.getCursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no existe"));

        t.setTitulo(dto.getTitulo());
        t.setMensaje(dto.getMensaje());
        t.setAutor(autor);
        t.setCurso(curso);

        return map(t);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!topicos.existsById(id)) throw new EntityNotFoundException("Tópico no encontrado");
        topicos.deleteById(id);
    }

    private TopicoResponseDTO map(Topico t) {
        return new TopicoResponseDTO(
                t.getId(), t.getTitulo(), t.getMensaje(), t.getFechaCreacion(),
                t.getStatus().name(), t.getAutor().getNombre(), t.getCurso().getNombre()
        );
    }
}
