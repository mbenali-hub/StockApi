package com.ben3li.stockapi.conrtoladores;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.CrearInventarioDTO;
import com.ben3li.stockapi.dto.InsertarProductosDTO;
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
    public ResponseEntity<InventarioDTO> crearInventario(
        @RequestBody CrearInventarioDTO crearInventarioDTO,
         HttpServletRequest request)
    {

        InventarioDTO inventarioDTO = inventarioServiceImpl.crearInventario(
                                                                            crearInventarioDTO.getUbicacionId(), 
                                                                            crearInventarioDTO.getInventarioDTO(), 
                                                                            //UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce")
                                                                            UUID.fromString("348b7171-e6b6-4525-a893-a8f838613165")
                                                                            //(UUID)request.getAttribute("userId")
        ); 
        
        return new ResponseEntity<InventarioDTO>(inventarioDTO,HttpStatus.CREATED);                                                             
    }

    @PostMapping("/{inventarioId}/productos")
    public ResponseEntity<InventarioDTO> insertarProductos(
        @PathVariable UUID inventarioId, 
        @RequestBody InsertarProductosDTO insertarProductosDTO,
        HttpServletRequest request)
        { 
            
        //InventarioDTO inventarioDTO= inventarioServiceImpl.insertarProductos(inventarioId, insertarProductosDTO.getProductos(), (UUID)request.getAttribute("userId"));
        InventarioDTO inventarioDTO= inventarioServiceImpl.insertarProductos(inventarioId, insertarProductosDTO.getProductos(), UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        return new ResponseEntity<>( inventarioDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/{inventarioId}")
    public ResponseEntity<Void> eliminarInventario(
        @PathVariable UUID inventarioId,
        HttpServletRequest request
    ){
        // inventarioServiceImpl.eliminarInventario(inventarioId, (UUID)request.getAttribute("userId"));
        inventarioServiceImpl.eliminarInventario(inventarioId, UUID.fromString("b174b122-1175-42a0-8093-d6bd7cada15e"));
        return ResponseEntity.noContent().build();
    }
}
