package com.ben3li.stockapi.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    private UUID id;
    private String nombre;
    private List<UsuarioUbicacionDTO> usuarios;

}
