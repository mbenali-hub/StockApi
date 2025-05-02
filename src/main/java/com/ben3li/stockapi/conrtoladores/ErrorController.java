package com.ben3li.stockapi.conrtoladores;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.dto.ApiErrorResponse;
import com.ben3li.stockapi.excepciones.AccesoDenegadoException;
import com.ben3li.stockapi.excepciones.ConflictosDeDatosException;
import com.ben3li.stockapi.excepciones.RecursoNoEncontradoException;

@ControllerAdvice
@RestController
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(ex.getStatusCode().value())
                                                            .mensaje(ex.getMessage())
                                                            .build();
                                                            
        return new ResponseEntity<>(apiErrorResponse,ex.getStatusCode());
    }

    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<ApiErrorResponse> handleAccesoDenegadoException(AccesoDenegadoException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(HttpStatus.FORBIDDEN.value())
                                                            .mensaje(ex.getMessage())
                                                            .build();
                                                            
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictosDeDatosException.class)
    public ResponseEntity<ApiErrorResponse> handleConflictosDeDatosException(ConflictosDeDatosException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(HttpStatus.CONFLICT.value())
                                                            .mensaje(ex.getMessage())
                                                            .build();
                                                            
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(HttpStatus.NOT_FOUND.value())
                                                            .mensaje(ex.getMessage())
                                                            .build();
                                                            
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(HttpStatus.CONFLICT.value())
                                                            .mensaje(ex.getMessage())
                                                            .build();
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.CONFLICT);
   }
}
