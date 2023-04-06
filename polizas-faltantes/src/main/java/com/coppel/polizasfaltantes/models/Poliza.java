package com.coppel.polizasfaltantes.models;

import java.sql.Date;

@lombok.AllArgsConstructor
@lombok.Getter
public class Poliza {
    private int IdPoliza;
    private int IdUsuario;
    private int IdEmpleadoGenero;
    private Empleado EmpleadoGenero;
    private String SKU;
    private ProductoInventario ProductoInventario;
    private int Cantidad;
    private String Observaciones;
    private Date Fecha;
    private String MotivoEliminacion;
    private Date FechaEliminacion;
}
