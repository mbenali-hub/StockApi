package com.ben3li.stockapi.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,UUID> {

    @Query("SELECT p from Producto p join p.inventario i join i.ubicacion u where u.id =:ubicacionId ")
    public List<Producto> obtenerProductosDeUbicacion(@Param("ubicacionId") UUID ubicacionId);

    @Query("SELECT p from Producto p join p.inventario i join i.ubicacion u where u.id =:ubicacionId and p.nombre =:nombre")
    public List<Producto> obtenerProductosDeUbicacionPorNombre(@Param("ubicacionId") UUID ubicacionId, @Param("nombre") String nombre);


    List<Producto> findByIdInAndInventarioId(List<UUID> ids, UUID inventarioId);

    List<Producto> findByIdIn(List<UUID> productoIds);
}
