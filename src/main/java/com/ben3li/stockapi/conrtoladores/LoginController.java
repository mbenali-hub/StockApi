package com.ben3li.stockapi.conrtoladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.LoginRegistroRequest;
import com.ben3li.stockapi.dto.LoginResponse;
import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.servicios.AuthenticationService;
import com.ben3li.stockapi.servicios.UsuarioService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi")
public class LoginController {
    
    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRegistroRequest loginRequest){
        UserDetails userdetails=authenticationService.autenticar(loginRequest.getEmail(), loginRequest.getPassword());

        String token= authenticationService.generarToken(userdetails);

        LoginResponse loginResponse= LoginResponse.builder()
                                                    .token(token)
                                                    .expiraEn(860000)
                                                    .build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(@RequestBody LoginRegistroRequest loginRegistroRequest){
        UsuarioDTO usuarioCreadoDTO= usuarioService.guardarUsuario(loginRegistroRequest);
        return new ResponseEntity<>(usuarioCreadoDTO,HttpStatus.CREATED);
    }
}
