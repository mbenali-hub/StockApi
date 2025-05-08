package com.ben3li.stockapi.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,UUID> {

    public List<Producto> findByUbicacion_Id(UUID ubicacionId);
    public List<Producto> findByNombreAndUbicacion_Id(String nombre, UUID ubicacionId);
    List<Producto> findByIdIn(List<UUID> productoIds);
}
