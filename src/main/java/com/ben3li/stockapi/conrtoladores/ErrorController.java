package com.ben3li.stockapi.conrtoladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ben3li.stockapi.dto.ApiErrorResponse;

@ControllerAdvice
@RestController
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException ex){
        ApiErrorResponse apiErrorResponse= ApiErrorResponse.builder()
                                                            .status(HttpStatus.BAD_REQUEST.value())
                                                            .mensaje("Ya existe un usuario con ese email")
                                                            .build();
                                                            
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.BAD_REQUEST);
    }
}
