package com.ben3li.stockapi.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_ubicacion")
public class UsuarioUbicacion {
    public enum Rol{JEFE,ADMINISTRADOR,LECTOR}
   
    @EmbeddedId
    private UsuarioUbicacionId id;

    @ManyToOne()
    @JoinColumn(name = "usuario_id",nullable = false, updatable = false, insertable = false)
    private Usuario usuario;

    @ManyToOne()
    @JoinColumn(name = "ubicacion_id",nullable = false, updatable = false, insertable = false)
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}
