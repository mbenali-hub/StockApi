package com.ben3li.stockapi.repositorios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;

@Repository
public interface UsuarioUbicacionRepositorio extends JpaRepository<UsuarioUbicacion,UsuarioUbicacionId>{
    boolean existsByUsuarioIdAndUbicacionNombre(UUID usuarioId, String nombre);
    List<UsuarioUbicacion>findByUsuarioId(UUID usuarioId);
}
