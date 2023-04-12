package com.coppel.polizasfaltantes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coppel.polizasfaltantes.exceptions.UsuarioNoSePudoRegistrarException;
import com.coppel.polizasfaltantes.models.JWTResponse;
import com.coppel.polizasfaltantes.models.LoginRequest;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

    @PostMapping("login")
	public JWTResponse login(
        @RequestBody LoginRequest loginRequest
    ) {
        return authService.login(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );
	}

    @PostMapping("register")
    public JWTResponse register(
        @RequestBody UsuarioRegistroRequest registerRequest
    ) {

        authService.register(registerRequest);

        return authService.login(
            registerRequest.getEmail(),
            registerRequest.getPassword()
        );

    }

}
