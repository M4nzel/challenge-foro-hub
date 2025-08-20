package com.forohub.ForoHub.bootstrap;

import com.forohub.ForoHub.domain.*;
import com.forohub.ForoHub.domain.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeedRunner implements CommandLineRunner {

    private final UsuarioRepository usuarios;
    private final PerfilRepository perfiles;
    private final CursoRepository cursos;
    private final TopicoRepository topicos;

    @Override
    public void run(String... args) {
        var roleUser = perfiles.findByNombre("ROLE_USER").orElseGet(() -> {
            var p = new Perfil(); p.setNombre("ROLE_USER"); return perfiles.save(p);
        });

        var user = usuarios.findByCorreoElectronico("user@demo.com").orElseGet(() -> {
            var u = new Usuario();
            u.setNombre("Usuario Demo");
            u.setCorreoElectronico("user@demo.com");
            u.setContrasena("solo_para_probar"); // más adelante usarás BCrypt
            u.getPerfiles().add(roleUser);
            return usuarios.save(u);
        });

        var curso = cursos.findAll().stream().findFirst().orElseGet(() -> {
            var c = new Curso(); c.setNombre("Java y Spring"); c.setCategoria("Backend"); return cursos.save(c);
        });

        if (!topicos.existsByTituloAndMensaje("Hola Foro", "Mi primer tópico")) {
            var t = new Topico();
            t.setTitulo("Hola Foro");
            t.setMensaje("Mi primer tópico");
            t.setAutor(user);
            t.setCurso(curso);
            topicos.save(t);
        }
    }
}
