package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ben3li.stockapi.entidades.Inventario;

public interface InventarioRepositorio extends JpaRepository<Inventario,UUID>{

}
