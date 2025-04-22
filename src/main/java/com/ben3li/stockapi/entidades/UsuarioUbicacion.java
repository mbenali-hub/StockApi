package com.ben3li.stockapi.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "usuario_ubicacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario","ubicacion"})
public class UsuarioUbicacion {
    public enum Rol{JEFE,ADMINISTRADOR,LECTOR}
   
    @EmbeddedId
    private UsuarioUbicacionId id;

    @ManyToOne()
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id",nullable = false, updatable = false, insertable = false)
    private Usuario usuario;

    @ManyToOne()
    @MapsId("ubicacionId")
    @JoinColumn(name = "ubicacion_id",nullable = false, updatable = false, insertable = false)
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}
