package com.mibarrio.api.dto.oferta;

public class OfertaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private Long comercioId;
    private String comercioNombre;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Long getComercioId() {
        return comercioId;
    }
    public void setComercioId(Long comercioId) {
        this.comercioId = comercioId;
    }
    public String getComercioNombre() {
        return comercioNombre;
    }
    public void setComercioNombre(String comercioNombre) {
        this.comercioNombre = comercioNombre;
    }

}