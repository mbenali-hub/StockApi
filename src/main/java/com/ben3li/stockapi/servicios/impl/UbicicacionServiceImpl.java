package com.ben3li.stockapi.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.mappers.UbicacionMapper;
import com.ben3li.stockapi.repositorios.UbicacionRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioUbicacionRepositorio;
import com.ben3li.stockapi.servicios.UbicacionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UbicicacionServiceImpl implements UbicacionService{

    private final UbicacionRepositorio ubicacionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioUbicacionRepositorio usuarioUbicacionRepositorio;
    private final UbicacionMapper ubicacionMapper;
    @Override
    public Ubicacion crearUbicacion(UbicacionDTO ubicacionDTO, UUID userId) {
        if(usuarioUbicacionRepositorio.existsByUsuarioIdAndUbicacionNombre(userId, ubicacionDTO.getNombre())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ya existe una ubicacion con ese nombre");
        }

        Usuario usuario=usuarioRepositorio.findById(userId).orElseThrow();
        Ubicacion ubicacion=ubicacionMapper.fromDto(ubicacionDTO);
        ubicacion.setId(null);

        Ubicacion ubicacionGuardada=ubicacionRepositorio.save(ubicacion);


        UsuarioUbicacionId id= new UsuarioUbicacionId(usuario.getId(),ubicacionGuardada.getId());
        UsuarioUbicacion usuarioUbicacion= UsuarioUbicacion.builder()
                                                            .id(id)
                                                            .usuario(usuario)
                                                            .ubicacion(ubicacionGuardada)
                                                            .rol(Rol.JEFE)
                                                            .build();
                                                            
        usuarioUbicacionRepositorio.save(usuarioUbicacion);

        Ubicacion ubicacionActualizada = agregarUsuarioUbicacion(ubicacionGuardada, usuarioUbicacion);


        return ubicacionActualizada;
    }

    public Ubicacion agregarUsuarioUbicacion(Ubicacion ubicacion, UsuarioUbicacion usuarioUbicacion) {
        List<UsuarioUbicacion> usuarioUbicaciones = ubicacion.getUsuarioUbicacion();
    
        if (usuarioUbicaciones == null) {
            usuarioUbicaciones = new ArrayList<>();
            ubicacion.setUsuarioUbicacion(usuarioUbicaciones);
        }
    
        usuarioUbicaciones.add(usuarioUbicacion);
        return ubicacionRepositorio.save(ubicacion);
    }

    @Override
    public UbicacionDTO anhadirUsuarioAUbicacion(UUID ubicacionId, UUID usuarioId, Rol rol) {
        Ubicacion ubicacionActualizada=null;
        Ubicacion ubicacion= ubicacionRepositorio.findById(ubicacionId)
                                                .orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"La ubicacion no existe" ));
        Usuario usuario= usuarioRepositorio.findById(usuarioId)
                                            .orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"La usuario no existe" ));
        
        if(ubicacion!=null && usuario!=null){
            UsuarioUbicacionId id= new UsuarioUbicacionId(usuarioId, ubicacionId);
            UsuarioUbicacion usuarioUbicacion= usuarioUbicacionRepositorio.save( UsuarioUbicacion.builder()
                                                                                                .id(id)
                                                                                                .usuario(usuario)
                                                                                                .ubicacion(ubicacion)
                                                                                                .rol(rol)
                                                                                                .build()
            );
            
           ubicacionActualizada = agregarUsuarioUbicacion(ubicacion, usuarioUbicacion);
        }

        return ubicacionMapper.toDto(ubicacionActualizada);
    }
    
    
}
