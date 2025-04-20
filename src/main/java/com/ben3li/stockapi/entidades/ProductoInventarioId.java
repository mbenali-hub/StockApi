package com.ben3li.stockapi.entidades;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductoInventarioId implements Serializable{
    private UUID producto;
    private UUID inventario;
}
