package com.coppel.polizasfaltantes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioEmailExistException extends ResponseStatusException {
    public UsuarioEmailExistException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
