package com.coppel.polizasfaltantes.models;

@lombok.AllArgsConstructor
@lombok.Getter
public class Empleado {
    private int IdEmpleado;
    private String Nombre;
    private String Apellido;
    private Puesto Puesto;
}
