package com.ben3li.stockapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRegistroRequest {
    private String nombre;
    private String email;
    private String password;
}
