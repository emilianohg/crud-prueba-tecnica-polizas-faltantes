package com.coppel.polizasfaltantes.repositories.sqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.coppel.polizasfaltantes.components.SimpleJdbcCallFactory;
import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.repositories.UsuariosRepository;


@Repository
public class SqlServerUsuariosRepository implements UsuariosRepository {

    @Autowired
    SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Usuario> findByEmail(String email) {

        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Usuarios_ConsultarPorEmail")
            .declareParameters(
                new SqlParameter("Email", Types.VARCHAR)
            )
            .returningResultSet("usuarios", new UsuarioRowMapper());

        Map<String, Object> result = jdbcCall.execute(email);

        List<Usuario> usuarios = (List<Usuario>) result.get("usuarios");

        return usuarios.stream().findFirst();        
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }


    @Override
    public Optional<Usuario> store(UsuarioRegistroRequest usuario) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Usuarios_Registrar")
            .declareParameters(
                new SqlParameter("Email", Types.VARCHAR),
                new SqlParameter("Pass", Types.VARCHAR),
                new SqlParameter("Nombre", Types.VARCHAR),
                new SqlParameter("Apellido", Types.VARCHAR),
                new SqlParameter("IdPuesto", Types.INTEGER)
            )
            .returningResultSet("usuarios", new UsuarioRowMapper());

        Map<String, Object> result = jdbcCall.execute(
            usuario.getEmail(),
            usuario.getPassword(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getIdPuesto()
        );

        List<Usuario> usuarios = (List<Usuario>) result.get("usuarios");

        return usuarios.stream().findFirst();        
    }

    private class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Puesto puesto = new Puesto(
                rs.getInt("IdPuestoEmpleado"),
                rs.getString("NombrePuestoEmpleado")
            );

            Empleado empleado = new Empleado(
                rs.getInt("IdEmpleado"),
                rs.getString("NombreEmpleado"), 
                rs.getString("ApellidoEmpleado"),
                puesto
            );
            
            return new Usuario(
                rs.getInt("IdUsuario"),
                rs.getString("Email"),
                rs.getString("Pass"),
                rs.getInt("IdEmpleado"),
                empleado
            );

        }
    }
    
}
