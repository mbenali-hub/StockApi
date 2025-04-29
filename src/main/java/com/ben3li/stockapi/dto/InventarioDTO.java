package com.ben3li.stockapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario.Tipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ben3li.stockapi.entidades.ProductoInventario;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.Usuario;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {

    private UUID id;

    private String nombre;

    private Tipo tipo;

    private LocalDateTime fechaDeCreacion;

    private Usuario creador;

    private Ubicacion ubicacion;

    private List<ProductoInventario> productoInventarios;

    private boolean activo;
}
