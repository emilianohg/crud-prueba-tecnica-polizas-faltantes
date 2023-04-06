package com.coppel.polizasfaltantes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.JWTResponse;
import com.coppel.polizasfaltantes.models.LoginRequest;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;


    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest(
            "test@example.com",
            "password"
        );

        JWTResponse jwtResponse = getJWTResponse();

        when(authService.login("test@example.com", "password"))
            .thenReturn(jwtResponse);

        assertEquals(jwtResponse, authController.login(loginRequest));

        verify(
            authService,
            times(1)
        ).login("test@example.com", "password");
    }


    @Test
    public void testRegisterSuccess() {
        UsuarioRegistroRequest registerRequest = getRegistroRequest();

        Usuario usuario = getUsuario();
        
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        JWTResponse jwtResponse = getJWTResponse();

        when(authService.register(registerRequest))
            .thenReturn(usuarioOptional);

        when(authService.login("test@example.com", "password"))
            .thenReturn(jwtResponse);

        assertEquals(jwtResponse, authController.register(registerRequest));

        verify(
            authService,
            times(1)
        ).register(registerRequest);

        verify(
            authService,
            times(1)
        ).login("test@example.com", "password");
    }


    @Test
    public void testRegisterThrowsException() {
        UsuarioRegistroRequest registerRequest = getRegistroRequest();

        Optional<Usuario> usuarioOptional = Optional.empty();

        when(authService.register(registerRequest))
            .thenReturn(usuarioOptional);

        assertThrows(RuntimeException.class, () -> {
            authController.register(registerRequest);
        });

        verify(
            authService,
            times(1)
        ).register(registerRequest);

        verify(
            authService,
            times(0)
        ).login(null, null);
    }

    private JWTResponse getJWTResponse() {
        return new JWTResponse(
            1L,
            "token",
            "admin@example.com",
            new Date()
        );
    }

    private Usuario getUsuario() {
        return new Usuario(
            1,
            "test@example.com",
            "encrypted-password",
            1,
            new Empleado(
                1,
                "John",
                "Doe",
                new Puesto(
                    1,
                    "Administrador"
                )
            )
        );
    }

    private UsuarioRegistroRequest getRegistroRequest() {
        return new UsuarioRegistroRequest(
            "test@example.com",
            "password",
            "John",
            "Doe",
            1
        );
    }
}
