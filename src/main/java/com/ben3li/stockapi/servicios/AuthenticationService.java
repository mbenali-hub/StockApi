package com.ben3li.stockapi.servicios;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    UserDetails autenticar(String email, String password);
    String generarToken(UserDetails userDetails);
    UserDetails validarToken(String token);
}
