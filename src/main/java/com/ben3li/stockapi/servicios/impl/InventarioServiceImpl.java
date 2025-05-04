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
import com.ben3li.stockapi.dto.ProductoInventarioDTO;
import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.Producto;
import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.ProductoInventarioId;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.entidades.Inventario.Tipo;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.excepciones.AccesoDenegadoException;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.mappers.InventarioMapper;
import com.ben3li.stockapi.mappers.ProductoInventarioMapper;
import com.ben3li.stockapi.mappers.ProductoMapper;
import com.ben3li.stockapi.repositorios.InventarioRepositorio;
import com.ben3li.stockapi.repositorios.ProductoRepositorio;
import com.ben3li.stockapi.repositorios.UbicacionRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioUbicacionRepositorio;
import com.ben3li.stockapi.servicios.InventarioService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService{

    private final InventarioRepositorio inventarioRepositorio;
    private final UsuarioUbicacionRepositorio usuarioUbicacionRepositorio;
    private final UbicacionRepositorio ubicacionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProductoRepositorio productoRepositorio;


    private final ProductoMapper productoMapper;
    private final InventarioMapper inventarioMapper;

    private Usuario usuarioActual;
    private UsuarioUbicacion usuarioUbicacion;


    @Override
    public InventarioDTO obtenerInventario(UUID iventarioId, UUID userId) {

        Inventario inventario=getInventario(iventarioId);
        verificarUsuarioEnUbicacion(userId, inventario.getUbicacion().getId());
        return inventarioMapper.toDto(inventario);
    }


    @Transactional
    @Override
    public InventarioDTO crearInventario(UUID ubicacionId, InventarioDTO inventarioDTO,UUID userId) {
        
        Ubicacion ubicacion= getUbicacion(ubicacionId);
        usuarioActual=getUsuario(userId);

        usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, ubicacionId));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE){
            throw new AccesoDenegadoException("El usuario no tiene los permisos para crear inventarios en esta ubicacion");
        }

         
        Inventario inventario= Inventario.builder()
                                        .nombre(inventarioDTO.getNombre())
                                        .tipo(inventarioDTO.getTipo())
                                        .ubicacion(ubicacion)
                                        .creador(usuarioActual)
                                        .build();
        inventario=inventarioRepositorio.save(inventario);

        return inventarioMapper.toDto(inventario);
    }


    @Transactional
    @Override
    public InventarioDTO insertarProductos(UUID inventarioId, List<ProductoDTO> productosDTO,UUID userId) {
        Inventario inventario = getInventario(inventarioId);
        usuarioActual=getUsuario(userId);

        usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));

        if(usuarioUbicacion.getRol()==Rol.JEFE || usuarioUbicacion.getRol()==Rol.ADMINISTRADOR){
            List<Producto> productos=crearListaDeProductoInventario(productosDTO, inventario);
            inventario.getProductos().addAll(productos);
            inventario=inventarioRepositorio.save(inventario);
        }
        else{
            throw new AccesoDenegadoException("No tienes permisos para insertar productos.");
        }

        return inventarioMapper.toDto(inventario);
    }


    
    @Override
    public InventarioDTO updateInventario(UUID inventarioId, InventarioDTO nuevoInventario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInventario'");
    }
    
    
    @Transactional
    @Override
    public void eliminarInventario(UUID inventarioId,UUID userId) {
        Inventario inventario = getInventario(inventarioId);
        
        usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE){
            throw new AccesoDenegadoException("No tiene permisos para eliminar un ninventario en esta ubicacion");
        }
        
        inventarioRepositorio.deleteById(inventarioId);
    }
    
    @Transactional
    @Override
    public void eliminarProductosDelInventario(UUID inventarioId ,List<UUID> productosID, UUID userId) {
        Inventario inventario = getInventario(inventarioId);
        usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE && usuarioUbicacion.getRol()!=Rol.ADMINISTRADOR){
            throw new AccesoDenegadoException("No tienes permisos para eliminar productos de este inventario en esta ubicacion.");
        }
        
        for (UUID idProducto : productosID) {
            Producto producto = productoRepositorio.findById(idProducto)
            .orElseThrow(()->new RecursoNoEncontradoException("No se ha encontrado el producto con id: "+idProducto));
            
            producto.setInventario(null);
            productoRepositorio.save(producto);
        }
        
    }
    
    
    private List<Producto> crearListaDeProductoInventario(List<ProductoDTO> productosIniciales, Inventario inventarioGuardado)
    {
        List<Producto> productos= new ArrayList<>();
        for (ProductoDTO productoDTO : productosIniciales) {
            Producto producto = productoMapper.fromDto(productoDTO);
            producto.setInventario(inventarioGuardado);
            productos.add(producto);                                                
        }
        return productos;
    }
    
    private Usuario getUsuario(UUID usuarioId) {
        return usuarioRepositorio.findById(usuarioId)
        .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe" ));
    }
    
    private Ubicacion getUbicacion(UUID ubicacionId) {
        return ubicacionRepositorio.findById(ubicacionId)
        .orElseThrow(()->new RecursoNoEncontradoException("La ubicacion no existe" ));
    }
    
    private Inventario getInventario(UUID inventarioId) {
        Inventario inventario = inventarioRepositorio.findById(inventarioId)
                                         .orElseThrow(()-> new RecursoNoEncontradoException("No se ha encontrado el inventario."));
        return inventario;
    }

    private UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe en la ubicacion" ));
    }


    private void verificarUsuarioEnUbicacion(UUID userId, UUID ubicacionId) {
        usuarioUbicacionRepositorio.findById(new UsuarioUbicacionId(userId, ubicacionId))
                                    .orElseThrow(() -> new RecursoNoEncontradoException("El usuario no existe en la ubicación. Por lo que no puedes obtener acceso a sus inventarios."));
    }
    

}
