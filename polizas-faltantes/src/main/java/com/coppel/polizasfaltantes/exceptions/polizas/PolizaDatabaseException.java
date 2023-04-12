package com.coppel.polizasfaltantes.exceptions.polizas;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PolizaDatabaseException extends ResponseStatusException {
    public PolizaDatabaseException() {
        this("Ocurrio un error al intentar crear la poliza");
    }

    public PolizaDatabaseException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
