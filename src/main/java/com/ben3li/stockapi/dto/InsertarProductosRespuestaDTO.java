package com.ben3li.stockapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertarProductosRespuestaDTO {
    private InventarioDTO inventarioActualizado;
    private String mensajeError;
    private List<ProductoDTO> productosNoIsertados;
}
