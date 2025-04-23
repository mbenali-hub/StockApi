package com.ben3li.stockapi.servicios;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ben3li.stockapi.dto.LoginRegistroRequest;
import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.entidades.Usuario;

public interface UsuarioService {
    UsuarioDTO guardarUsuario(LoginRegistroRequest loginRegistroRequest);
    Page<UsuarioDTO> buscarUsuariosPorNombre(String nombre,int page,int size);
}
