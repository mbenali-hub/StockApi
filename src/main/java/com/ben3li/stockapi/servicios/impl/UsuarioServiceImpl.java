package com.ben3li.stockapi.servicios.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ben3li.stockapi.dto.LoginRegistroRequest;
import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.excepciones.ConflictosDeDatosException;
import com.ben3li.stockapi.mappers.UsuarioMapper;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UsuarioDTO guardarUsuario(LoginRegistroRequest loginRegistroRequest) {
       if(usuarioRepositorio.existsByEmail(loginRegistroRequest.getEmail())){
           throw new ConflictosDeDatosException("Ya existe un usuario con ese email");
       }
       loginRegistroRequest.setPassword(passwordEncoder.encode(loginRegistroRequest.getPassword()));
       Usuario usuarioGuardado=usuarioRepositorio.save(usuarioMapper.fromDTO(loginRegistroRequest));
       return usuarioMapper.toDto(usuarioGuardado);
    }
    
    @Override
    public Page<UsuarioDTO> buscarUsuariosPorNombre(String nombre,int page,int size) {
        Pageable pageable=PageRequest.of(page, size);
        Page<UsuarioDTO> dtoPage= usuarioRepositorio.findByNombre(nombre,pageable)
                                            .map(usuarioMapper::toDto);
        return dtoPage;
    }

    

}
