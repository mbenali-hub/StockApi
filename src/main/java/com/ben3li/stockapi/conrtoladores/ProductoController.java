package com.ben3li.stockapi.conrtoladores;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.ProductoCantidadUpdateDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.servicios.impl.ProductoServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockapi/ubicacion/{ubicacionId}/productos")
public class ProductoController {

    private final ProductoServiceImpl productoServiceImpl;

    @GetMapping()
    public ResponseEntity<List<ProductoDTO>> obtenerProductoEnUbicacion(
        @PathVariable UUID ubicacionId,
        HttpServletRequest request
    ){
        //List<ProductoDTO> productos = productoServiceImpl.getProductos(ubicacionId,(UUID) request.getAttribute("userId"));
        List<ProductoDTO> productos = productoServiceImpl.getProductos(ubicacionId,UUID.fromString("b174b122-1175-42a0-8093-d6bd7cada15e"));
        return new ResponseEntity<>(productos,HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<List<ProductoDTO>> actualizarCantidadesDeProductos(
        @PathVariable UUID ubicacionId,
        @RequestBody List<ProductoCantidadUpdateDTO> productos,
        HttpServletRequest request
    )
    {
        List<ProductoDTO> productosActualizados = productoServiceImpl.updateCantidadProducto(ubicacionId, productos, UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
        //(UUID)request.getAttribute("userId"));
        return new ResponseEntity<>(productosActualizados,HttpStatus.OK);
    }
}
