package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,UUID> {

}
