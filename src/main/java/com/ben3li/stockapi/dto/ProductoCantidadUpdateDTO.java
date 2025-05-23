package com.ben3li.stockapi.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCantidadUpdateDTO {
    private UUID productoId;
    private int nuevaCantidad;
}

