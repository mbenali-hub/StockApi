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
import com.ben3li.stockapi.mappers.InventarioMapper;
import com.ben3li.stockapi.mappers.ProductoInventarioMapper;
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
    private final ProductoInventarioMapper productoInventarioMapper;

    private Usuario usuarioActual;
    private UsuarioUbicacion usuarioUbicacion;

    @Transactional
    @Override
    public InventarioDTO crearInventario(UUID ubicacionId, InventarioDTO inventarioDTO,
            List<ProductoDTO> productosIniciales,UUID userId) {
        
        Ubicacion ubicacion= getUbicacion(ubicacionId);
        usuarioActual=getUsuario(userId);

        usuarioUbicacion=getUsuarioUbicacion(new UsuarioUbicacionId(userId, ubicacionId));
        
        if(usuarioUbicacion.getRol()!=Rol.JEFE){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"El usuario no tiene los permisos para crear inventarios en esta ubicacion");
        }

        Inventario inventario= Inventario.builder()
                                        .nombre(inventarioDTO.getNombre())
                                        .tipo(inventarioDTO.getTipo())
                                        .ubicacion(ubicacion)
                                        .creador(usuarioActual)
                                        .build();
        inventario=inventarioRepositorio.save(inventario);
        System.out.println("El inventario gaurdado: "+inventario);

        List<ProductoInventario> productos=crearListaDeProductoInventario(productosIniciales, inventario);
        inventario.setProductoInventarios(productos);

        List<ProductoInventarioDTO> productosDTO= productos.stream().map(productoInventarioMapper::toDto).toList();
        InventarioDTO inventarioDTO2 = inventarioMapper.toDto(inventarioRepositorio.save(inventario));
        inventarioDTO2.setProductoInventarios(productosDTO);

        return inventarioDTO2;
    }



    private List<ProductoInventario> crearListaDeProductoInventario(List<ProductoDTO> productosIniciales, Inventario inventarioGuardado)
    {
        List<ProductoInventario> productos= new ArrayList<>();
        for (ProductoDTO productoDTO : productosIniciales) {
            Producto producto = productoMapper.fromDto(productoDTO);
            productoRepositorio.save(producto);
            
            ProductoInventarioId id= new ProductoInventarioId(producto.getId(),inventarioGuardado.getId());
            System.out.println("El inventario que se va a asociar: "+inventarioGuardado);
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

    private UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"El usuario no existe en la ubicacion" ));
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
