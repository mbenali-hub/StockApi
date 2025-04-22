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
        System.out.println("Ubicacion: " + ubicacionGuardada+"????"+"Usuario: "+usuario);



        UsuarioUbicacionId id= new UsuarioUbicacionId(usuario.getId(),ubicacionGuardada.getId());
        UsuarioUbicacion usuarioUbicacion= UsuarioUbicacion.builder()
                                                            .id(id)
                                                            .usuario(usuario)
                                                            .ubicacion(ubicacionGuardada)
                                                            .rol(Rol.JEFE)
                                                            .build();
                                                            
        usuarioUbicacionRepositorio.save(usuarioUbicacion);

        List<UsuarioUbicacion>usuarioUbicaciones=ubicacionGuardada.getUsuarioUbicacion();
        if(usuarioUbicaciones!=null)
            {usuarioUbicaciones.add(usuarioUbicacion);
        }else{
            usuarioUbicaciones=new ArrayList<>();
            usuarioUbicaciones.add(usuarioUbicacion);
            ubicacionGuardada.setUsuarioUbicacion(usuarioUbicaciones);
        }

        ubicacionRepositorio.save(ubicacionGuardada);
        return ubicacionGuardada;
    }

}
