package com.ben3li.stockapi.servicios;


import org.springframework.data.domain.Page;

import com.ben3li.stockapi.dto.LoginRegistroRequest;
import com.ben3li.stockapi.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO guardarUsuario(LoginRegistroRequest loginRegistroRequest);
    Page<UsuarioDTO> buscarUsuariosPorNombre(String nombre,int page,int size);
}
