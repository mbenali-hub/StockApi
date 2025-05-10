package com.ben3li.stockapi.servicios;

import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacionId;

public interface BusquedaDeEntidadesService {
    Usuario getUsuario(UUID usuarioId);

    Ubicacion getUbicacion(UUID ubicacionId);

    Inventario getInventario(UUID inventarioId);

    UsuarioUbicacion getUsuarioUbicacion(UsuarioUbicacionId id);

    void verificarUsuarioEnUbicacion(UUID userId, UUID ubicacionId);
}
