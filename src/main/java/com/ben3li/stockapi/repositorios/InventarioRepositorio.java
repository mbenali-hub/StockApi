package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Inventario;

@Repository
public interface InventarioRepositorio extends JpaRepository<Inventario,UUID>{

    @Query("SELECT i FROM Inventario i left join fetch i.productos where i.id =:id")
    Inventario findByIdWithProductos(@Param("id") UUID inventarioId);
}
