package com.ben3li.stockapi.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ben3li.stockapi.dto.InsertarProductosDTO;
import com.ben3li.stockapi.dto.InsertarProductosRespuestaDTO;
import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.Producto;
import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.ProductoInventarioId;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.excepciones.AccesoDenegadoException;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.mappers.InventarioMapper;
import com.ben3li.stockapi.mappers.ProductoMapper;
import com.ben3li.stockapi.repositorios.InventarioRepositorio;
import com.ben3li.stockapi.repositorios.ProductoInventarioRepositorio;
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
    private final ProductoInventarioRepositorio productoInventarioRepositorio;

    private final ProductoMapper productoMapper;
    private final InventarioMapper inventarioMapper;


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
        Usuario usuarioActual =getUsuario(userId);

        UsuarioUbicacion usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, ubicacionId));
        
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
    public InsertarProductosRespuestaDTO insertarProductos(UUID inventarioId, List<ProductoDTO> productosDTO,UUID userId) {

        Inventario inventario = getInventario(inventarioId);
        UsuarioUbicacion usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));

        if(usuarioUbicacion.getRol()!=Rol.JEFE && usuarioUbicacion.getRol()!=Rol.ADMINISTRADOR){
            throw new AccesoDenegadoException("No tienes permisos para insertar productos.");
        }
       
        List<ProductoInventario> relaciones=new ArrayList<>();    

        List<Producto> productosRecibidos = productosDTO.stream().map(productoMapper::fromDto).toList();
        List<UUID> idsRecibidos = productosRecibidos.stream().map(Producto::getId).toList();

        List<Producto> productosExistentes= productoRepositorio.findByIdIn(idsRecibidos);
        List<UUID> idsExistentes = productosExistentes.stream().map(Producto::getId).toList();

       

        List<ProductoDTO> productosNoEncontrados = productosDTO.stream()
                                                                .filter(dto-> !idsExistentes.contains(dto.getId()))
                                                                .toList();

      
        Map<UUID,Integer> productosCantidad = productosDTO.stream()
                                                            .filter(dto-> dto.getId()!=null)
                                                            .collect(Collectors.toMap(ProductoDTO::getId,ProductoDTO::getCantidad));
        for (Producto producto : productosExistentes) {
           
            ProductoInventarioId id = new ProductoInventarioId(producto.getId(), inventarioId);
            ProductoInventario productoInventario = ProductoInventario.builder()
                                                                .id(id)
                                                                .producto(producto)
                                                                .inventario(inventario)
                                                                .cantidad(productosCantidad.get(producto.getId()))
                                                                .build();
            relaciones.add(productoInventario);
        } 
        
        productoInventarioRepositorio.saveAll(relaciones);
        inventario = inventarioRepositorio.findByIdWithProductos(inventarioId);
        
        InsertarProductosRespuestaDTO insertarProductosDTO = new InsertarProductosRespuestaDTO();
        insertarProductosDTO.setProductosNoIsertados(productosNoEncontrados);
        insertarProductosDTO.setInventarioActualizado(inventarioMapper.toDto(inventario));

        if(productosNoEncontrados.size()>0){
            insertarProductosDTO.setMensajeError("Hay productos que no han sido insertados. ");
        }
        else{
            insertarProductosDTO.setMensajeError("Se han insertado todos los productos. ");
        }
        return insertarProductosDTO;
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
        
        UsuarioUbicacion usuarioUbicacion = getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE){
            throw new AccesoDenegadoException("No tiene permisos para eliminar un inventario en esta ubicacion");
        }
        
        productoInventarioRepositorio.deleteById_InventarioId(inventarioId);
        inventarioRepositorio.deleteById(inventarioId);
    }
    

    @Transactional
    @Override
    public void eliminarProductosDelInventario(UUID inventarioId ,List<UUID> productosID, UUID userId) {
        Inventario inventario = getInventario(inventarioId);
        UsuarioUbicacion usuarioUbicacion = getUsuarioUbicacion(new UsuarioUbicacionId(userId, inventario.getUbicacion().getId()));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE && usuarioUbicacion.getRol()!=Rol.ADMINISTRADOR){
            throw new AccesoDenegadoException("No tienes permisos para eliminar productos de este inventario en esta ubicacion.");
        }
        
        List<ProductoInventario> relaciones = productoInventarioRepositorio.findById_InventarioIdAndId_ProductoIdIn(inventarioId,productosID);
           
        if(relaciones.size()!=productosID.size()){
            throw new RecursoNoEncontradoException("Uno o más productos no se han encontrado");
        }

        productoInventarioRepositorio.deleteAllById_ProductoIdIn(productosID);   
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
        return inventarioRepositorio.findById(inventarioId)
                                         .orElseThrow(()-> new RecursoNoEncontradoException("No se ha encontrado el inventario."));
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
