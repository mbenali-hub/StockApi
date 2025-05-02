package com.ben3li.stockapi.entidades;

import java.util.List;
import java.util.UUID;

import com.ben3li.stockapi.entidades.Inventario.Tipo;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "productos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nombre", "inventario_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;
    
    @Column(nullable = false)
    private String unidad;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    // @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    // private List<ProductoInventario> productoInventarios;
    

}
