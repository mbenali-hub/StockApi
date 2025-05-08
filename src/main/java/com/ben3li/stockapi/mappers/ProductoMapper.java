package com.ben3li.stockapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ben3li.stockapi.dto.ProductoDTO;
import com.ben3li.stockapi.entidades.Producto;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductoMapper {
    Producto fromDto(ProductoDTO productoDTO);


    ProductoDTO toDto(Producto producto);
}
