package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.LoginRegistroRequest;
import com.ben3li.stockapi.dto.UsuarioDTO;
import com.ben3li.stockapi.entidades.Usuario;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    Usuario fromDTO(LoginRegistroRequest loginRegistroRequest);
    UsuarioDTO toDto(Usuario usuario);
}
