package com.ben3li.stockapi.servicios;

import java.util.UUID;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;

public interface UbicacionService {
    Ubicacion crearUbicacion(UbicacionDTO ubicacionDTO,UUID userId);
}
