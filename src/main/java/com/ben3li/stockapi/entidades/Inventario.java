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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "creador_id",nullable = false)
    private Usuario creador;

    @OneToOne
    @JoinColumn(name = "ubicacion_id",nullable = false)
    private Ubicacion ubicacion;

    @ManyToMany(mappedBy = "inventarios")
    private List<Producto> productos;
    private LocalDateTime fechaDeCreacion;
    private boolean activo;
}
