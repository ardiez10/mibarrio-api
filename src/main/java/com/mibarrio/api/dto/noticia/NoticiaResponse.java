package com.mibarrio.api.dto.noticia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private String fuente;
    private String url;
    private LocalDate fechaPublicacion;
}