package com.coppel.polizasfaltantes.models;

@lombok.AllArgsConstructor
@lombok.Getter
public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
    }

    
}
