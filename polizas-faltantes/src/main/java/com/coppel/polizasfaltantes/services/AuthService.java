package com.coppel.polizasfaltantes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coppel.polizasfaltantes.components.jwt.JWTManager;
import com.coppel.polizasfaltantes.exceptions.CredencialesIncorrectasException;
import com.coppel.polizasfaltantes.exceptions.UsuarioEmailExistException;
import com.coppel.polizasfaltantes.models.JWTResponse;
import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.repositories.UsuariosRepository;

@Service
public class AuthService {

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JWTManager jwtManager;

    @Autowired
	PasswordEncoder encoder;

    @Autowired
    private UsuariosRepository usuariosRepository;


    public JWTResponse login(String email, String pass) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, pass)
            );
        } catch (BadCredentialsException e) {
            throw new CredencialesIncorrectasException();
        }
        

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String token = jwtManager.generateToken(userDetails);

		return new JWTResponse(
            userDetails.getId(),
            token,
            userDetails.getUsername(),
            jwtManager.extractExpiration(token)
        );
    }
    
    public Optional<Usuario> register(UsuarioRegistroRequest usuarioRequest) {

        if (usuariosRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new UsuarioEmailExistException("El email ya existe");
        }

        usuarioRequest.setPassword(encoder.encode(usuarioRequest.getPassword()));

        return usuariosRepository.store(usuarioRequest);
    }
}
