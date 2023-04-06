package com.coppel.polizasfaltantes.models;

import java.util.Date;

@lombok.AllArgsConstructor
@lombok.RequiredArgsConstructor
@lombok.Getter
public class JWTResponse {
    private Long IdUsuario;
    private String Token;
    private String Email;
    private Date TiempoExpiracion;
    private String Type = "Bearer";

    public JWTResponse(
        Long idUsuario,
        String token,
        String email,
        Date tiempoExpiracion
    ) {
        IdUsuario = idUsuario;
        Token = token;
        Email = email;
        TiempoExpiracion = tiempoExpiracion;
    }

}
