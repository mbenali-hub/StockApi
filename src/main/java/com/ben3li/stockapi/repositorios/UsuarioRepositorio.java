package com.ben3li.stockapi.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.stockapi.entidades.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,UUID>{
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
