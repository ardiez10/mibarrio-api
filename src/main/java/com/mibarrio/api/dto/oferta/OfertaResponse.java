package com.mibarrio.api.dto.oferta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private Long comercioId;
    private String comercioNombre;
}