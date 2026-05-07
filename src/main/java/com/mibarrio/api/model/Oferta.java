package com.mibarrio.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "comercio_id")
    private Comercio comercio;

    public Oferta() {
    }

    public Long getId() {
        return id;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}