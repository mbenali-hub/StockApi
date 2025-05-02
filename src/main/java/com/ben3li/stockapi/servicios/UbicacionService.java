package com.ben3li.stockapi.servicios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;

public interface UbicacionService {
    Ubicacion crearUbicacion(UbicacionDTO ubicacionDTO,UUID userId);
    UbicacionDTO anhadirUsuarioAUbicacion(UUID ubicacionId, UUID usuarioId, Rol rol);
    UbicacionDTO quitarUsuarioDeUbicacion(UUID ubicacionId, UUID usuarioId);
    void eliminarUbicacion(UUID ubicacionId, UUID usuarioId);
    List<UbicacionDTO> listarUbicaciones(UUID usuarioId);
}
