package com.ben3li.stockapi.dto;

import java.util.UUID;

import com.ben3li.stockapi.entidades.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioDTO {
    private ProductoDTO producto;
    private UUID inventarioId;
    private int cantidad;
}
