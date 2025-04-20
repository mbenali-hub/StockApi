package com.ben3li.stockapi.entidades;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UsuarioUbicacionId implements Serializable{
    private UUID usuario;
    private UUID ubicacion;
}
