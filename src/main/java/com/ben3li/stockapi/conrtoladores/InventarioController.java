package com.ben3li.stockapi.conrtoladores;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.CrearInventarioDTO;
import com.ben3li.stockapi.dto.InsertarProductosRespuestaDTO;
import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.servicios.InventarioService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;


    @GetMapping("/{inventarioId}")
    public ResponseEntity<InventarioDTO> obtenerInventario (
        @PathVariable UUID inventarioId,
         HttpServletRequest request)
    {
        InventarioDTO inventarioDTO = inventarioService.obtenerInventario(inventarioId,
                                                                            (UUID) request.getAttribute("userId"));
                                                                             //UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        return new ResponseEntity<>(inventarioDTO,HttpStatus.OK);
    }
    
    @PostMapping()
    public ResponseEntity<InventarioDTO> crearInventario(
        @RequestBody CrearInventarioDTO crearInventarioDTO,
        HttpServletRequest request)
    {

        InventarioDTO inventarioDTO = inventarioService.crearInventario(
                                                                            crearInventarioDTO.getUbicacionId(), 
                                                                            crearInventarioDTO.getInventarioDTO(), 
                                                                            (UUID)request.getAttribute("userId")
                                                                            //UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce")
                                                                            //UUID.fromString("348b7171-e6b6-4525-a893-a8f838613165")
        ); 
        
        return new ResponseEntity<InventarioDTO>(inventarioDTO,HttpStatus.CREATED);                                                             
    }

    @PostMapping("/{inventarioId}/productos")
    public ResponseEntity<InsertarProductosRespuestaDTO> insertarProductos(
        @PathVariable UUID inventarioId, 
        @RequestBody List<ProductoDTO> productos,
        HttpServletRequest request)
    { 
        InsertarProductosRespuestaDTO respuestaDTO = inventarioService.insertarProductos(inventarioId, productos, (UUID)request.getAttribute("userId"));
        return new ResponseEntity<>(respuestaDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/{inventarioId}/productos")
    public ResponseEntity<Void> elimnarProductosDelInventario(
        @PathVariable UUID inventarioId,
        @RequestBody List<UUID> productos,
        HttpServletRequest request
    )
    {
        inventarioService.eliminarProductosDelInventario(inventarioId, productos, (UUID)request.getAttribute("userId"));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{inventarioId}")
    public ResponseEntity<Void> eliminarInventario(
        @PathVariable UUID inventarioId,
        HttpServletRequest request)
    {
        inventarioService.eliminarInventario(inventarioId, (UUID)request.getAttribute("userId"));
        //inventarioService.eliminarInventario(inventarioId, UUID.fromString("b174b122-1175-42a0-8093-d6bd7cada15e"));
        return ResponseEntity.noContent().build();
    }
}
