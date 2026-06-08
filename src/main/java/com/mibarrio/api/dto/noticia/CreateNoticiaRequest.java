package com.mibarrio.api.dto.noticia;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para crear una noticia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticiaRequest {

    @Schema(description = "Título de la noticia", example = "Nueva feria local")
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 500, message = "El título no puede superar los 500 caracteres")
    private String titulo;

    @Schema(description = "Descripción de la noticia", example = "Este fin de semana habrá una feria de comercio local en el barrio.")
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 65535, message = "La descripción no puede superar los 65535 caracteres")
    private String descripcion;

    @Schema(description = "Fuente de la noticia", example = "Asociación vecinal")
    @Size(max = 100, message = "La fuente no puede superar los 100 caracteres")
    private String fuente;

    @Schema(description = "URL externa de la noticia", example = "https://ejemplo.com/noticia")
    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    @Pattern(regexp = "^$|^(http|https)://.*$", message = "La URL debe ser válida")
    private String url;

    @Schema(description = "URL de la imagen de la noticia", example = "https://ejemplo.com/noticia.jpg")
    @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
    @Pattern(regexp = "^$|^(http|https)://.*$", message = "La URL de la imagen debe ser válida")
    private String imagenUrl;

    @Schema(description = "Fecha de publicación de la noticia", example = "2026-05-08")
    private LocalDate fechaPublicacion;
}
