package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,UUID> {
//     select p.* from productos p
// join inventarios i 
// 	on i.id=p.inventario_id
// join ubicacion u 
// 	on u.id=i.ubicacion_id
// where u.id = '6a2c6ab4-3da4-449a-b664-11a5061e46ef'
}
