package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.ProductoInventarioDTO;
import com.ben3li.stockapi.entidades.ProductoInventario;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = ProductoMapper.class)
public interface ProductoInventarioMapper {
   // @Mapping(source = "producto", target = "productoDTO")
    @Mapping(source= "inventario.id", target= "inventarioId")
    ProductoInventarioDTO toDto(ProductoInventario productoInventario);
}
