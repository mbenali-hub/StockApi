package com.ben3li.stockapi.conrtoladores;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.ProductoCantidadUpdateDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.servicios.ProductoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi/ubicacion/{ubicacionId}/productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping()
    public ResponseEntity<ProductoDTO> crearProducto(
        @PathVariable UUID ubicacionId,
        @RequestBody ProductoDTO productoDTO,
        HttpServletRequest request
    ){
        productoDTO = productoService.crearProducto(ubicacionId, productoDTO, (UUID) request.getAttribute("userId"));
        //productoDTO = productoService.crearProducto(ubicacionId, productoDTO, UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        return new ResponseEntity<>(productoDTO,HttpStatus.CREATED);
    }
    
    @GetMapping()
    public ResponseEntity<List<ProductoDTO>> obtenerProductoEnUbicacion(
        @PathVariable UUID ubicacionId,
        HttpServletRequest request
    ){
        List<ProductoDTO> productos = productoService.getProductos(ubicacionId,(UUID) request.getAttribute("userId"));
        //List<ProductoDTO> productos = productoService.getProductos(ubicacionId,UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        return new ResponseEntity<>(productos,HttpStatus.OK);
    }


    @GetMapping("/{nombre}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductoPorNombre(
        @PathVariable UUID ubicacionId,
        @PathVariable String nombre,
        HttpServletRequest request
    ){
        List<ProductoDTO> productos = productoService.getProductosPorNombre(nombre, ubicacionId, (UUID)request.getAttribute("userId"));
        //List<ProductoDTO> productos = productoService.getProductosPorNombre(nombre, ubicacionId, UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        return new ResponseEntity<>(productos,HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<List<ProductoDTO>> actualizarCantidadesDeProductos(
        @PathVariable UUID ubicacionId,
        @RequestBody List<ProductoCantidadUpdateDTO> productos,
        HttpServletRequest request
    )
    {
        List<ProductoDTO> productosActualizados = productoService.updateCantidadProducto(ubicacionId, productos, (UUID)request.getAttribute("userId"));
        return new ResponseEntity<>(productosActualizados,HttpStatus.OK);
    }


}
