package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.InventarioDTO;
import com.ben3li.stockapi.entidades.Inventario;
import com.ben3li.stockapi.entidades.ProductoInventario;

@Mapper(componentModel = "spring",unmappedTargetPolicy=ReportingPolicy.IGNORE, uses=ProductoInventarioMapper.class)
public interface InventarioMapper {
    
    InventarioDTO toDto(Inventario inventario);
}
