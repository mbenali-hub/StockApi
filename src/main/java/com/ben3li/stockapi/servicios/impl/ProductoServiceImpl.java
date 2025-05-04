package com.ben3li.stockapi.servicios.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ben3li.stockapi.dto.ProductoCantidadUpdateDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.entidades.Producto;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.excepciones.AccesoDenegadoException;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.mappers.ProductoMapper;
import com.ben3li.stockapi.repositorios.ProductoRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioUbicacionRepositorio;
import com.ben3li.stockapi.servicios.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepositorio productoRepositorio;
    private final UsuarioUbicacionRepositorio usuarioUbicacionRepositorio;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoDTO> getProductos(UUID ubicacionId, UUID userId) {
        UsuarioUbicacion usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, ubicacionId));

        if(usuarioUbicacion.getRol()!=Rol.JEFE&& usuarioUbicacion.getRol()!=Rol.ADMINISTRADOR){
            throw new AccesoDenegadoException("Solo el jefe y los administradores pueden ver los productos para añadir a un inventario.");
        }
        
        List<Producto> productos = productoRepositorio.obtenerProductosDeUbicacion(ubicacionId);
        return productos.stream().map(productoMapper::toDto).toList();
    }

    @Override
    public List<ProductoDTO> updateCantidadProducto(List<ProductoCantidadUpdateDTO> productos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCantidadProducto'");
    }

    private UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe en la ubicacion" ));
    }

}
