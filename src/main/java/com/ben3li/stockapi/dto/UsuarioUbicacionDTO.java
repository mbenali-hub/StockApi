package com.ben3li.stockapi.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUbicacionDTO {
    private UUID usuarioId;
    private String nombreUsuario;
    private String rol;
}
