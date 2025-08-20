package com.forohub.ForoHub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "topicos",
        uniqueConstraints = @UniqueConstraint(name = "uq_topico_titulo_mensaje", columnNames = {"titulo","mensaje"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTopico status = StatusTopico.ABIERTO;

    @ManyToOne(optional = false) @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(optional = false) @JoinColumn(name = "curso_id")
    private Curso curso;
}
