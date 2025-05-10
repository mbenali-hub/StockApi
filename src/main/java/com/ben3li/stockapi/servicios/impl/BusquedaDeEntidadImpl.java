package com.ben3li.stockapi.servicios.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;
import com.ben3li.stockapi.repositorios.InventarioRepositorio;
import com.ben3li.stockapi.repositorios.UbicacionRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.repositorios.UsuarioUbicacionRepositorio;
import com.ben3li.stockapi.servicios.BusquedaDeEntidadesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusquedaDeEntidadImpl implements BusquedaDeEntidadesService{

    private final UsuarioRepositorio usuarioRepositorio;
    private final UbicacionRepositorio ubicacionRepositorio;
    private final InventarioRepositorio inventarioRepositorio;
    private final UsuarioUbicacionRepositorio usuarioUbicacionRepositorio;

    @Override
    public Usuario getUsuario(UUID usuarioId) {
        return usuarioRepositorio.findById(usuarioId)
        .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe" ));
    }

    @Override
    public Ubicacion getUbicacion(UUID ubicacionId) {
        return ubicacionRepositorio.findById(ubicacionId)
        .orElseThrow(()->new RecursoNoEncontradoException("La ubicación no existe" ));
    }

    @Override
    public Inventario getInventario(UUID inventarioId) {
        return inventarioRepositorio.findById(inventarioId)
                                         .orElseThrow(()-> new RecursoNoEncontradoException("No se ha encontrado el inventario."));
    }

    @Override
    public UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id) {
        return usuarioUbicacionRepositorio.findById(id)
                                            .orElseThrow(()->new RecursoNoEncontradoException("El usuario no existe en la ubicación" ));
    }

    @Override
    public void verificarUsuarioEnUbicacion(UUID userId, UUID ubicacionId) {
        usuarioUbicacionRepositorio.findById(new UsuarioUbicacionId(userId, ubicacionId))
                                    .orElseThrow(() -> new RecursoNoEncontradoException("El usuario no existe en la ubicación. Por lo que no puedes obtener acceso a sus inventarios."));
    }

}
