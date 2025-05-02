package com.ben3li.stockapi.excepciones;

public class RecursoNoEncontradoException extends RuntimeException{
    public RecursoNoEncontradoException (String mensaje){
        super(mensaje);
    }
}
