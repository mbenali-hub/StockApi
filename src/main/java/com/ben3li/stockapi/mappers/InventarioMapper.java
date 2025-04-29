package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.entidades.Inventario;

@Mapper(componentModel = "spring",unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface InventarioMapper {
    InventarioDTO toDto(Inventario inventario);
}
