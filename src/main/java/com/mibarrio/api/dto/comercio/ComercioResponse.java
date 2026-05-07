package com.mibarrio.api.dto.comercio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioResponse {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
}