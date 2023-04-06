package com.coppel.polizasfaltantes.repositories;

import java.util.Optional;

import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;

public interface UsuariosRepository {
    public Optional<Usuario> findByEmail(String email);
    public boolean existsByEmail(String email);
    public Optional<Usuario> store(UsuarioRegistroRequest usuarioRequest);
}
