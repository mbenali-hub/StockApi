package com.ben3li.stockapi.entidades;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Inventarios",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tipo","ubicacion_id"})
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventario {
    public enum Tipo {FRUTA,PESCADO,VERDURA,FRUTOS_SECOS,CARNE,BOLLERIA,ROPA,CALZADO,ACCESORIOS};
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    private LocalDateTime fechaDeCreacion;

    @ManyToOne
    @JoinColumn(name = "creador_id",nullable = false)
    private Usuario creador;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id",nullable = false)
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "inventario")
    private List<ProductoInventario> productoInventarios;

    @Column(nullable = false)
    private boolean activo;
}
