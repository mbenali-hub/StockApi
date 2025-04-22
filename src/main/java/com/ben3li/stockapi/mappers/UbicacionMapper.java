package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = UsuarioUbicacionMapper.class)
public interface UbicacionMapper {
    Ubicacion fromDto(UbicacionDTO ubicacionDTO);

    @Mapping(source = "usuarioUbicacion", target = "usuarios")
    UbicacionDTO toDto(Ubicacion ubicacion);
}
