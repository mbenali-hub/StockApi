package com.ben3li.stockapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkProductoCantidadUpdateDTO {
    private List<ProductoCantidadUpdateDTO> productos;    
}
