package com.coppel.polizasfaltantes.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private String Fecha;
    private String MotivoEliminacion;
    private String FechaEliminacion;

    public Poliza(
        int IdPoliza,
        int IdUsuario,
        int IdEmpleadoGenero,
        Empleado EmpleadoGenero,
        String SKU,
        ProductoInventario ProductoInventario,
        int Cantidad,
        String Observaciones,
        Timestamp Fecha,
        String MotivoEliminacion,
        Timestamp FechaEliminacion
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.IdPoliza = IdPoliza;
        this.IdUsuario = IdUsuario;
        this.IdEmpleadoGenero = IdEmpleadoGenero;
        this.EmpleadoGenero = EmpleadoGenero;   
        this.SKU = SKU;
        this.ProductoInventario = ProductoInventario;
        this.Cantidad = Cantidad;
        this.Observaciones = Observaciones;
        this.Fecha = formatter.format(Fecha);
        this.MotivoEliminacion = MotivoEliminacion;
        if (FechaEliminacion != null) {
            this.FechaEliminacion = formatter.format(FechaEliminacion);
        }
    }
    

}
