package com.ben3li.stockapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StockApiUserDetailsServirce implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario= usuarioRepositorio.findByEmail(email)
                                .orElseThrow(()-> new RecursoNoEncontradoException("No se ha encontrado usario con el email "+ email));
        return new StockApiUserDetails(usuario);                                                    
    }

}
