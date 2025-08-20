package com.forohub.ForoHub.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String categoria;
}
