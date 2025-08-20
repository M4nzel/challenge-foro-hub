package com.forohub.ForoHub.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // ROLE_USER, ROLE_ADMIN
}
