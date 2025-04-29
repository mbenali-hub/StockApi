package com.ben3li.stockapi.dto;

import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario.Tipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ben3li.stockapi.entidades.ProductoInventario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private UUID id;

    private String nombre;

    private Tipo tipo;
    
    private String unidad;

    private int cantidad;

    private List<ProductoInventario> productoInventarios;
}
