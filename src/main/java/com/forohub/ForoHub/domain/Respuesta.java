package com.forohub.ForoHub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Respuesta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @ManyToOne(optional = false) @JoinColumn(name = "topico_id")
    private Topico topico;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(optional = false) @JoinColumn(name = "autor_id")
    private Usuario autor;

    @Column(nullable = false)
    private boolean solucion = false;
}
