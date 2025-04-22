package com.ben3li.stockapi.conrtoladores;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.mappers.UbicacionMapper;
import com.ben3li.stockapi.servicios.impl.UbicicacionServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stockapi")
@RequiredArgsConstructor
public class GestorStockController {
    private final UbicicacionServiceImpl ubicicacionServiceImpl;
    private final UbicacionMapper ubicacionMapper;
    @PostMapping("/crearubicacion")
    public UbicacionDTO crearUbicacion(@RequestBody UbicacionDTO ubicacionDTO,HttpServletRequest request){
        Ubicacion ubicacion = ubicicacionServiceImpl.crearUbicacion(ubicacionDTO,(UUID)request.getAttribute("userId"));
        return ubicacionMapper.toDto(ubicacion);        
    }
}
