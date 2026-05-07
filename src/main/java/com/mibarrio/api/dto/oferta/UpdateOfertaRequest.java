package com.mibarrio.api.dto.oferta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfertaRequest {

    private String titulo;
    private String descripcion;
}