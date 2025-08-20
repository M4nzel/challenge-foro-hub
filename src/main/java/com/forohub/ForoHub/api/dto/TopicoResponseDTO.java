package com.forohub.ForoHub.api.dto;

import java.time.LocalDateTime;

public class TopicoResponseDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autorNombre;
    private String cursoNombre;

    public TopicoResponseDTO() {}

    public TopicoResponseDTO(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion,
                             String status, String autorNombre, String cursoNombre) {
        this.id = id; this.titulo = titulo; this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion; this.status = status;
        this.autorNombre = autorNombre; this.cursoNombre = cursoNombre;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public String getStatus() { return status; }
    public String getAutorNombre() { return autorNombre; }
    public String getCursoNombre() { return cursoNombre; }

    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setStatus(String status) { this.status = status; }
    public void setAutorNombre(String autorNombre) { this.autorNombre = autorNombre; }
    public void setCursoNombre(String cursoNombre) { this.cursoNombre = cursoNombre; }
}
