package com.ben3li.stockapi.servicios.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
       if(usuarioRepositorio.existsByEmail(usuario.getEmail())){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ya existe un usuario con ese email");
       }
       usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
       return usuarioRepositorio.save(usuario);
    }

}
