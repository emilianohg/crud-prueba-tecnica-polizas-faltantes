package com.coppel.polizasfaltantes.models;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@lombok.AllArgsConstructor
@lombok.Getter
public class Usuario {
    private int IdUsuario;
    private String Email;
    @JsonIgnore
    private String Pass;
    private int IdEmpleado;
    private Empleado Empleado;

    public List<String> getRoles() {
        return Arrays.asList("ROLE_USER");
    }
}
