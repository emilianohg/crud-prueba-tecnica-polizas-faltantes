package com.coppel.polizasfaltantes.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.coppel.polizasfaltantes.components.jwt.JWTManager;
import com.coppel.polizasfaltantes.exceptions.CredencialesIncorrectasException;
import com.coppel.polizasfaltantes.exceptions.UsuarioEmailExistException;
import com.coppel.polizasfaltantes.exceptions.UsuarioNoSePudoRegistrarException;
import com.coppel.polizasfaltantes.models.JWTResponse;
import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.repositories.UsuariosRepository;
import com.coppel.polizasfaltantes.repositories.mocks.MockUsuariosRepository;

public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTManager jwtManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private AuthService authService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testLogin() {
        String email = "test@example.com";
        String pass = "password";

        Usuario usuario = getUsuario(1, email);
        

        UserDetails userDetails = UserDetailsImpl.build(usuario);
        Authentication authenticationMock = mock(Authentication.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            email,
            pass
        );

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);

        Date expiration = Date.from(new Date().toInstant().plusSeconds(3600));

        String token = "generated_jwt_token";
        when(jwtManager.generateToken(userDetails)).thenReturn(token);
        when(jwtManager.extractExpiration(token)).thenReturn(expiration);


        JWTResponse response = this.authService.login(email, pass);

        assert response.getIdUsuario() == Long.valueOf(usuario.getIdUsuario());
    }

    @Test
    public void testLogin__withInvalidCredentials() {
        String email = "test@example.com";
        String pass = "password-wrong";

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            email,
            pass
        );

        when(authenticationManager.authenticate(authenticationToken))
            .thenThrow(BadCredentialsException.class);

        assertThrows(CredencialesIncorrectasException.class, () -> {
            this.authService.login(email, pass);
        });
    }


    @Test
    public void testRegister() {

        String email = "test@example.com";
        String pass = "password";

        UsuarioRegistroRequest request = new UsuarioRegistroRequest(
            email,
            pass,
            "John",
            "Doe",
            1
        );

        Usuario usuario = getUsuario(1, email);

        when(usuariosRepository.existsByEmail(email)).thenReturn(false);

        when(encoder.encode(pass)).thenReturn("password-encrypted");

        when(usuariosRepository.store(request)).thenReturn(Optional.of(usuario));

        // act
        this.authService.register(request);

        // assert
        verify(usuariosRepository, times(1)).existsByEmail(email);
        verify(usuariosRepository, times(1)).store(request);

        assert request.getPassword().equals("password-encrypted");
    }


    @Test
    public void testRegister__withExistingEmail() {
        String email = "test@example.com";
        String pass = "password";

        UsuarioRegistroRequest request = new UsuarioRegistroRequest(
            email,
            pass,
            "John",
            "Doe",
            1
        );

        when(usuariosRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(UsuarioEmailExistException.class, () -> {
            this.authService.register(request);
        });
    }

    @Test
    public void testRegister__withErrorRegistering() {
        String email = "test@example.com";
        String pass = "password";

        UsuarioRegistroRequest request = new UsuarioRegistroRequest(
            email,
            pass,
            "John",
            "Doe",
            1
        );

        when(usuariosRepository.existsByEmail(email)).thenReturn(false);

        when(usuariosRepository.store(request)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoSePudoRegistrarException.class, () -> {
            this.authService.register(request);
        });
    }

    public Usuario getUsuario(int id, String email) {
        MockUsuariosRepository mockUsuariosRepository = new MockUsuariosRepository();
        return mockUsuariosRepository.generate(id, email);
    }

}
