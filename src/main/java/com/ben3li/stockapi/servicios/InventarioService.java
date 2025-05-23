package com.ben3li.stockapi.servicios;

import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.dto.InsertarProductosRespuestaDTO;
import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.dto.ProductoDTO;

public interface InventarioService {
    InventarioDTO crearInventario(UUID ubicacionId, InventarioDTO inventarioDTO,UUID userId);
    InventarioDTO updateInventario(UUID inventarioId, InventarioDTO nuevoInventario);
    InsertarProductosRespuestaDTO insertarProductos(UUID inventarioId, List<ProductoDTO> productos, UUID userId);
    InventarioDTO obtenerInventario(UUID iventarioId, UUID userId);
    void eliminarProductosDelInventario( UUID inventarioId,List<UUID> productosID, UUID userId);
    void eliminarInventario(UUID inventarioId, UUID userId);
}
