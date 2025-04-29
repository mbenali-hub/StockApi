package com.ben3li.stockapi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.ProductoInventarioId;

public interface ProductoInventarioRepositorio extends JpaRepository<ProductoInventario,ProductoInventarioId>{

}
