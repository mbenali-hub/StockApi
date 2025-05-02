package com.ben3li.stockapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario.Tipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ben3li.stockapi.entidades.ProductoInventario;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {

    private UUID id;

    private String nombre;

    private Tipo tipo;

    private LocalDateTime fechaDeCreacion;

    private UsuarioDTO creador;

    private UbicacionDTO ubicacion;

    private List<ProductoDTO> productos;

    private boolean activo;
}
