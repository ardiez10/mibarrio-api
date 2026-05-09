package com.mibarrio.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "horarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer diaSemana;

    private LocalTime horaApertura;

    private LocalTime horaCierre;

    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "comercio_id")
    private Comercio comercio;
}