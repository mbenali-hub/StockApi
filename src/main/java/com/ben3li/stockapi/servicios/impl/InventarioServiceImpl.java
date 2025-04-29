package com.ben3li.stockapi.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mapstruct.IterableMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.Producto;
import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.ProductoInventarioId;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.Inventario.Tipo;
import com.ben3li.stockapi.mappers.InventarioMapper;
import com.ben3li.stockapi.mappers.ProductoMapper;
import com.ben3li.stockapi.repositorios.InventarioRepositorio;
import com.ben3li.stockapi.repositorios.ProductoInventarioRepositorio;
import com.ben3li.stockapi.repositorios.UbicacionRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.servicios.InventarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService{

    private final InventarioRepositorio inventarioRepositorio;

    private final UbicacionRepositorio ubicacionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProductoMapper productoMapper;
    private final InventarioMapper inventarioMapper;
    private final ProductoInventarioRepositorio productoInventarioRepositorio;
    private Usuario usuarioActual;

    @Override
    public InventarioDTO crearInventario(UUID ubicacionId, InventarioDTO inventarioDTO,
            List<ProductoDTO> productosIniciales,UUID userId) {

        Inventario inventarioGuardado=null;
        
        Ubicacion ubicacion= getUbicacion(ubicacionId);
        usuarioActual=getUsuario(userId);

        if(ubicacion!=null){
            Inventario inventario= Inventario.builder()
                                            .nombre(inventarioDTO.getNombre())
                                            .tipo(inventarioDTO.getTipo())
                                            .ubicacion(ubicacion)
                                            .creador(usuarioActual)
                                            .build();
            inventarioGuardado=inventarioRepositorio.save(inventario);
        }

        List<ProductoInventario> productos=crearListaDeProductoInventario(productosIniciales, inventarioGuardado);

        productoInventarioRepositorio.saveAll(productos);
        inventarioGuardado.setProductoInventarios(productos);

        return inventarioMapper.toDto(inventarioRepositorio.save(inventarioGuardado));
    }

    private List<ProductoInventario> crearListaDeProductoInventario(List<ProductoDTO> productosIniciales, Inventario inventarioGuardado)
    {
        List<ProductoInventario> productos= new ArrayList<>();
        for (ProductoDTO productoDTO : productosIniciales) {
            Producto producto = productoMapper.fromDto(productoDTO);
            ProductoInventarioId id= new ProductoInventarioId(producto.getId(),inventarioGuardado.getId());
            ProductoInventario productoInventario= ProductoInventario.builder()
                                                                        .id(id)
                                                                        .producto(producto)
                                                                        .inventario(inventarioGuardado)
                                                                        .cantidad(productoDTO.getCantidad())
                                                                        .build();
            productos.add(productoInventario);                                                            
        }
        return productos;
    }

    private Usuario getUsuario(UUID usuarioId) {
        return usuarioRepositorio.findById(usuarioId)
                                    .orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"El usuario no existe" ));
    }

    private Ubicacion getUbicacion(UUID ubicacionId) {
        return ubicacionRepositorio.findById(ubicacionId)
                                    .orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"La ubicacion no existe" ));
    }

    @Override
    public InventarioDTO updateInventario(UUID inventarioId, InventarioDTO nuevoInventario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInventario'");
    }

    @Override
    public InventarioDTO insertarProductos(UUID inventarioId, List<ProductoDTO> productos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertarProductos'");
    }

    @Override
    public InventarioDTO eliminarInventario(UUID inventarioId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarInventario'");
    }

}
