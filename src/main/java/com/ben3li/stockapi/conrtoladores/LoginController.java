package com.ben3li.stockapi.conrtoladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.LoginRequest;
import com.ben3li.stockapi.dto.LoginResponse;
import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.mappers.UsuarioMapper;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.servicios.UsuarioService;
import com.ben3li.stockapi.servicios.impl.AuthenticationServiceImpl;
import com.ben3li.stockapi.servicios.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi")
public class LoginController {
    
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        UserDetails userdetails=authenticationServiceImpl.autenticar(loginRequest.getEmail(), loginRequest.getPassword());

        String token= authenticationServiceImpl.generarToken(userdetails);

        LoginResponse loginResponse= LoginResponse.builder()
                                                    .token(token)
                                                    .expiraEn(860000)
                                                    .build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario=  usuarioServiceImpl.guardarUsuario(usuarioMapper.fromDTO(usuarioDTO));
        UsuarioDTO usuarioCreadoDTO= UsuarioDTO.builder()
                                                .id(usuario.getId())
                                                .nombre(usuario.getNombre())
                                                .email(usuario.getEmail())
                                                .build();
        return new ResponseEntity<>(usuarioCreadoDTO,HttpStatus.CREATED);
    }
}
