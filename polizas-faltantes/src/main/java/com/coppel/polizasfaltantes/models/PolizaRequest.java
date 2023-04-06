package com.coppel.polizasfaltantes.models;

@lombok.AllArgsConstructor
@lombok.Getter
public class PolizaRequest {
    Long idUsuario;
    int idEmpleadoGenero;
    String sku;
    int cantidad;
    String observaciones;

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
