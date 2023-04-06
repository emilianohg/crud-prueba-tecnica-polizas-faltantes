package com.coppel.polizasfaltantes.models;


@lombok.AllArgsConstructor
@lombok.Getter
public class UsuarioRegistroRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private int idPuesto;

    public void setPassword(String password) {
        this.password = password;
    }
}
