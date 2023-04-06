package com.coppel.polizasfaltantes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PolizaNotFoundException extends ResponseStatusException {

    public PolizaNotFoundException() {
        this("No se encontró la poliza");
    }

    public PolizaNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
    
}
