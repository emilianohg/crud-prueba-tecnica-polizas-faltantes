package com.coppel.polizasfaltantes.repositories.mocks;

import java.util.Optional;

import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.repositories.EmpleadosRepository;
import com.coppel.polizasfaltantes.repositories.UsuariosRepository;

public class MockUsuariosRepository implements UsuariosRepository {

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.of(this.generate(1, email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return true;
    }

    @Override
    public Optional<Usuario> store(UsuarioRegistroRequest usuarioRequest) {
        return Optional.of(this.generate(1));
    }

    public Usuario generate(int id) {
        return this.generate(id, "john." + id + "@example.com");
    }

    public Usuario generate(int id, String email) {
        EmpleadosRepository empleadosRepository = new MockEmpleadoRepository();

        return new Usuario(
            id,
            email,
            "encrypted-password",
            id,
            empleadosRepository.getAll(1, 1).getRecords().get(0)
        );
    }

    
}
