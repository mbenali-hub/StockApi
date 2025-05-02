package com.ben3li.stockapi.excepciones;

public class ConflictosDeDatosException extends RuntimeException{
    public ConflictosDeDatosException(String mensaje){
        super(mensaje);
    }
}
