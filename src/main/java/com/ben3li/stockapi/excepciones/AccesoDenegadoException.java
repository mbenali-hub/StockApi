package com.ben3li.stockapi.excepciones;

public class AccesoDenegadoException extends RuntimeException{
    public AccesoDenegadoException (String mensaje){
        super(mensaje);
    }
}
