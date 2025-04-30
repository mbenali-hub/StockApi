package com.ben3li.stockapi.conrtoladores;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.CrearInventarioDTO;
import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.servicios.impl.InventarioServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi/inventarios")
public class InventarioController {

    private final InventarioServiceImpl inventarioServiceImpl;
    @PostMapping()
    public ResponseEntity<InventarioDTO> crearInventario(@RequestBody CrearInventarioDTO crearInventarioDTO, HttpServletRequest request){

        InventarioDTO inventarioDTO = inventarioServiceImpl.crearInventario(
                                                                            crearInventarioDTO.getUbicacionId(), 
                                                                            crearInventarioDTO.getInventarioDTO(), 
                                                                            crearInventarioDTO.getProductosIniciales(),
                                                                            UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce")
                                                                            //(UUID)request.getAttribute("userId")
        ); 
        
        return new ResponseEntity<InventarioDTO>(inventarioDTO,HttpStatus.CREATED);                                                             
    }
}
