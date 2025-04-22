package com.ben3li.stockapi.entidades;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUbicacionId implements Serializable{
    private UUID usuarioId;
    private UUID ubicacionId;
}
