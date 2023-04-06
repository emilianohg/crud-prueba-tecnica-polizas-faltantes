package com.coppel.polizasfaltantes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CredencialesIncorrectasException extends ResponseStatusException {

    public CredencialesIncorrectasException() {
        this( "Credenciales incorrectas");
    }

    public CredencialesIncorrectasException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
    
}
