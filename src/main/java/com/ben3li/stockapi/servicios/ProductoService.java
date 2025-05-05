package com.ben3li.stockapi.servicios;

import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.dto.ProductoCantidadUpdateDTO;
import com.ben3li.stockapi.dto.ProductoDTO;

public interface ProductoService {
    List<ProductoDTO> getProductos(UUID ubicacionId, UUID userId);
    List<ProductoDTO> updateCantidadProducto(UUID ubicacionId,List<ProductoCantidadUpdateDTO> productos, UUID userId);
}
