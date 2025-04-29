package com.ben3li.stockapi.servicios;

import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.dto.ProductoDTO;

public interface InventarioService {
    InventarioDTO crearInventario(UUID ubicacionId, InventarioDTO inventarioDTO, List<ProductoDTO> productosIniciales,UUID userId);
    InventarioDTO updateInventario(UUID inventarioId, InventarioDTO nuevoInventario);
    InventarioDTO insertarProductos(UUID inventarioId, List<ProductoDTO> productos);
    InventarioDTO eliminarInventario(UUID inventarioId);
}
