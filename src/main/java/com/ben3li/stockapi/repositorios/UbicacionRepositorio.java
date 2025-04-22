package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Ubicacion;

@Repository
public interface UbicacionRepositorio extends JpaRepository<Ubicacion,UUID>{

}
