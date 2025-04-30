package com.ben3li.stockapi.entidades;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioId implements Serializable{
    private UUID productoId;
    private UUID inventarioId;
}
