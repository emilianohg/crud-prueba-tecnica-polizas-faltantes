package com.coppel.polizasfaltantes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioNoSePudoRegistrarException extends ResponseStatusException {

    public UsuarioNoSePudoRegistrarException() {
        this( "El usuario no se pudo registrar correctamente");
    }

    public UsuarioNoSePudoRegistrarException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
    
}
