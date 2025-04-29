package com.ben3li.stockapi.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class ProductoInventario {

    @EmbeddedId
    private ProductoInventarioId id;

    @ManyToOne
    @JoinColumn(name="producto_id",nullable = false, updatable = false, insertable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="inventario_id",nullable = false, updatable = false, insertable = false)
    private Inventario inventario;

    @Column(nullable = false)
    private int cantidad;
}
