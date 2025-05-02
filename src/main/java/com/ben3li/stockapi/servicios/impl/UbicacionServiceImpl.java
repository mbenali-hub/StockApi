package com.ben3li.stockapi.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.excepciones.AccesoDenegadoException;
import com.ben3li.stockapi.excepciones.ConflictosDeDatosException;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.mappers.UbicacionMapper;
import com.ben3li.stockapi.repositorios.UbicacionRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioUbicacionRepositorio;
import com.ben3li.stockapi.servicios.UbicacionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService{

    private UsuarioUbicacionId id;
    private final UbicacionRepositorio ubicacionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioUbicacionRepositorio usuarioUbicacionRepositorio;
    private final UbicacionMapper ubicacionMapper;
    @Override
    public Ubicacion crearUbicacion(UbicacionDTO ubicacionDTO, UUID userId) {
        if(usuarioUbicacionRepositorio.existsByUsuarioIdAndUbicacionNombre(userId, ubicacionDTO.getNombre())){
            throw new ConflictosDeDatosException("Ya existe una ubicacion con ese nombre");
        }

        Usuario usuario=usuarioRepositorio.findById(userId).orElseThrow();
        Ubicacion ubicacion=ubicacionMapper.fromDto(ubicacionDTO);
        ubicacion.setId(null);

        Ubicacion ubicacionGuardada=ubicacionRepositorio.save(ubicacion);


        id= new UsuarioUbicacionId(usuario.getId(),ubicacionGuardada.getId());
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
        Ubicacion ubicacion= getUbicacion(ubicacionId);
        Usuario usuario= getUsuario(usuarioId);
        
        if(ubicacion!=null && usuario!=null){
            id= new UsuarioUbicacionId(usuarioId, ubicacionId);
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

    

    
    @Override
    public UbicacionDTO quitarUsuarioDeUbicacion(UUID ubicacionId, UUID usuarioId) {
        Ubicacion ubicacion= getUbicacion(ubicacionId);
        UsuarioUbicacionId id= new UsuarioUbicacionId(usuarioId, ubicacionId);
        UsuarioUbicacion usuarioUbicacion=getUsuarioUbicacion(id);
        
        ubicacion.getUsuarioUbicacion().remove(usuarioUbicacion);
        usuarioUbicacionRepositorio.delete(usuarioUbicacion);
        return ubicacionMapper.toDto(ubicacionRepositorio.save(ubicacion));
    }
    
    
    @Transactional
    @Override
    public void eliminarUbicacion(UUID ubicacionId, UUID usuarioId) {

        Ubicacion ubicacion=getUbicacion(ubicacionId);
        UsuarioUbicacion usuarioUbicacion= getUsuarioUbicacion(new UsuarioUbicacionId(usuarioId, ubicacionId));

        if(usuarioUbicacion.getRol()!=Rol.JEFE){
            throw new AccesoDenegadoException("No tienes permisos para eliminar esta ubicacion");
        }

        usuarioUbicacionRepositorio.delete(usuarioUbicacion);
        ubicacionRepositorio.delete(ubicacion);
    }
    

    @Override
    public List<UbicacionDTO> listarUbicaciones(UUID usuarioId) {

        List<UsuarioUbicacion> usuarioUbicaciones = usuarioUbicacionRepositorio.findByUsuarioId(usuarioId);

        List<Ubicacion> ubicaciones= new ArrayList<>();

        for (UsuarioUbicacion usuarioUbicacion : usuarioUbicaciones) {
            ubicaciones.add(usuarioUbicacion.getUbicacion());
        }

        return ubicaciones.stream().map(ubicacionMapper::toDto).toList();
    }

     private Usuario getUsuario(UUID usuarioId) {
        return usuarioRepositorio.findById(usuarioId)
                                    .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe" ));
    }

    private Ubicacion getUbicacion(UUID ubicacionId) {
        return ubicacionRepositorio.findById(ubicacionId)
                                    .orElseThrow(()->new RecursoNoEncontradoException("La ubicacion no existe" ));
    }

    private UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe en la ubicacion" ));
    }
    
}
