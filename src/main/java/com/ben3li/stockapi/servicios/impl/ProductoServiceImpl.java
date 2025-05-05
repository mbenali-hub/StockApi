package com.ben3li.stockapi.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public List<ProductoDTO> updateCantidadProducto(UUID ubicacionId, List<ProductoCantidadUpdateDTO> productos, UUID userId) {
        UsuarioUbicacion usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, ubicacionId));

        if(usuarioUbicacion.getRol()!=Rol.JEFE&& usuarioUbicacion.getRol()!=Rol.ADMINISTRADOR){
            throw new AccesoDenegadoException("Solo el jefe y los administradores pueden actualizar los productos.");
        }
        
        List<UUID> ids = productos.stream().map(ProductoCantidadUpdateDTO::getProductoId).toList();
        List<Producto> productosEncontrados = productoRepositorio.findAllById(ids);

        if(productosEncontrados.size()!=ids.size()){
            throw new RecursoNoEncontradoException("Uno o más productos no se han encuentrado.");
        }

        Map<UUID,Integer> nuevasCantidades = productos.stream().collect(Collectors.toMap(
                                                                            ProductoCantidadUpdateDTO::getProductoId , 
                                                                            ProductoCantidadUpdateDTO::getNuevaCantidad)
        );

        for (Producto producto : productosEncontrados) {
            producto.setCantidad(nuevasCantidades.get(producto.getId()));
        }

        return productosEncontrados.stream().map(productoMapper::toDto).toList();

    }

    private UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe en la ubicacion" ));
    }

}
