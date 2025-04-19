package com.ben3li.stockapi.entidades;

import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario.Tipo;

import jakarta.persistence.Id;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private int cantidad;
    private Inventario inventario;
    private Tipo tipo;
    private String unidad;

}
