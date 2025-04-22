package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ben3li.stockapi.dto.UsuarioUbicacionDTO;
import com.ben3li.stockapi.entidades.UsuarioUbicacion;

@Mapper(componentModel = "spring")
public interface UsuarioUbicacionMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "nombreUsuario")
    UsuarioUbicacionDTO toDTO(UsuarioUbicacion entity);
}