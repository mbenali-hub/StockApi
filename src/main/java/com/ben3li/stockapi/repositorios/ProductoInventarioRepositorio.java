package com.ben3li.stockapi.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.ProductoInventarioId;

@Repository
public interface ProductoInventarioRepositorio extends JpaRepository<ProductoInventario,ProductoInventarioId>{

    void deleteById_InventarioId(UUID inventarioId);
    void deleteAllById_ProductoIdIn(List<UUID> productosId);

    List<ProductoInventario> findById_InventarioIdAndId_ProductoIdIn(UUID inventarioId,List<UUID> productoID);
}
