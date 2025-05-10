package com.ben3li.stockapi.conrtoladores;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    @GetMapping("/{nombre}")
    public ResponseEntity<Page<UsuarioDTO>> buscarUsuariosPorNombre(@PathVariable String nombre,@RequestParam int page,@RequestParam int size){
        return ResponseEntity.ok(usuarioService.buscarUsuariosPorNombre(nombre,page,size));
    }
}
