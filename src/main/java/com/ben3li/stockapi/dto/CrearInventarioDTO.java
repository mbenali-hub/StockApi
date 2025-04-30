package com.ben3li.stockapi.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearInventarioDTO {
    private UUID ubicacionId;
    private InventarioDTO inventarioDTO;
    private List<ProductoDTO> productosIniciales;
}
