package com.ben3li.stockapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertarProductosDTO {
    private List<ProductoDTO> productos;
}
