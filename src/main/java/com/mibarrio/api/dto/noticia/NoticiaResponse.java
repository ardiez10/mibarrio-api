package com.mibarrio.api.dto.noticia;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de una noticia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaResponse {

    @Schema(description = "Identificador de la noticia", example = "1")
    private Long id;

    @Schema(description = "Título de la noticia", example = "Nueva feria local")
    private String titulo;

    @Schema(description = "Descripción de la noticia", example = "Este fin de semana habrá una feria de comercio local en el barrio.")
    private String descripcion;

    @Schema(description = "Fuente de la noticia", example = "Asociación vecinal")
    private String fuente;

    @Schema(description = "URL externa de la noticia", example = "https://ejemplo.com/noticia")
    private String url;

    @Schema(description = "URL de la imagen de la noticia", example = "https://ejemplo.com/noticia.jpg")
    private String imagenUrl;

    @Schema(description = "Fecha de publicación de la noticia", example = "2026-05-08")
    private LocalDate fechaPublicacion;

    @Schema(description = "Indica si la noticia está activa", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de creación de la noticia", example = "2026-05-09T11:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización de la noticia", example = "2026-05-09T12:45:00")
    private LocalDateTime updatedAt;
}
