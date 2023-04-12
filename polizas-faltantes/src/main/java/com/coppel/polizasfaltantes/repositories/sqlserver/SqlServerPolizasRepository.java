package com.coppel.polizasfaltantes.repositories.sqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.coppel.polizasfaltantes.components.SimpleJdbcCallFactory;
import com.coppel.polizasfaltantes.exceptions.polizas.PolizaDatabaseException;
import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.repositories.PolizasRepository;

@Repository
public class SqlServerPolizasRepository implements PolizasRepository {

    public final static int DEFAULT_LIMIT = 10;

    @Autowired
    SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Pagination<Poliza> getAll() {
        return this.getAll(1);
    }

    @Override
    public Pagination<Poliza> getAll(int page) {
        return this.getAll(page, DEFAULT_LIMIT);
    }

    @Override
    public Pagination<Poliza> getAll(int page, int limit) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Polizas_Listar")
            .declareParameters(
                new SqlParameter("limit", Types.INTEGER),
                new SqlParameter("offset", Types.INTEGER),
                new SqlOutParameter("total", Types.INTEGER)
            )
            .returningResultSet("polizas", new PolizasRowMapper());

        int offset = ((page - 1) * limit);

        Map<String, Object> result = jdbcCall.execute(limit, offset);

        List<Poliza> empleados = (List<Poliza>) result.get("polizas");
        int totalRecords = (int) result.get("total");

        return new Pagination<Poliza>(
            page,
            limit,
            totalRecords,
            empleados
        );
    }

    @Override
    public Optional<Poliza> findById(
        int idPoliza
    ) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Polizas_Consultar")
            .declareParameters(
                new SqlParameter("IdPoliza", Types.INTEGER)
            )
            .returningResultSet("polizas", new PolizasRowMapper());


        Map<String, Object> result = jdbcCall.execute(idPoliza);

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }

    @Override
    public Optional<Poliza> store(
        PolizaRequest polizaRequest
    ) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Polizas_Registrar")
            .declareParameters(
                new SqlParameter("IdUsuario", Types.INTEGER),
                new SqlParameter("IdEmpleadoGenero", Types.INTEGER),
                new SqlParameter("SKU", Types.VARCHAR),
                new SqlParameter("Cantidad", Types.INTEGER),
                new SqlParameter("Observaciones", Types.LONGVARCHAR)
            )
            .returningResultSet("polizas", new PolizasRowMapper());

        Map<String, Object> result = null;

        try {
            result = jdbcCall.execute(
                polizaRequest.getIdUsuario(),
                polizaRequest.getIdEmpleadoGenero(),
                polizaRequest.getSku(),
                polizaRequest.getCantidad(),
                polizaRequest.getObservaciones()
            );
        } catch (DataAccessException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLException) {
                int errorCode = ((SQLException) rootCause).getErrorCode();

                if (errorCode > 50000) { // Errores de negocio en el procedimiento almacenado
                    throw new PolizaDatabaseException(rootCause.getMessage());
                }
            }
            
            throw e;
        }

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }

    @Override
    public Optional<Poliza> update(
        int idPoliza,
        PolizaRequest polizaRequest
    ) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Polizas_Actualizar")
            .declareParameters(
                new SqlParameter("IdPoliza", Types.INTEGER),
                new SqlParameter("IdUsuario", Types.INTEGER),
                new SqlParameter("IdEmpleadoGenero", Types.INTEGER),
                new SqlParameter("SKU", Types.VARCHAR),
                new SqlParameter("Cantidad", Types.INTEGER),
                new SqlParameter("Observaciones", Types.LONGVARCHAR)
            )
            .returningResultSet("polizas", new PolizasRowMapper());

        Map<String, Object> result;

        try {
            result = jdbcCall.execute(
                idPoliza,
                polizaRequest.getIdUsuario(),
                polizaRequest.getIdEmpleadoGenero(),
                polizaRequest.getSku(),
                polizaRequest.getCantidad(),
                polizaRequest.getObservaciones()
            );
        } catch (DataAccessException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLException) {
                int errorCode = ((SQLException) rootCause).getErrorCode();

                if (errorCode > 50000) { // Errores de negocio en el procedimiento almacenado
                    throw new PolizaDatabaseException(rootCause.getMessage());
                }
            }
            
            throw e;
        }

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }


    @Override
    public Optional<Poliza> delete(
        int idPoliza,
        String motivoEliminacion
    ) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Polizas_Eliminar")
            .declareParameters(
                new SqlParameter("IdPoliza", Types.INTEGER),
                new SqlParameter("MotivoEliminacion", Types.LONGVARCHAR)
            )
            .returningResultSet("polizas", new PolizasRowMapper());

            Map<String, Object> result;
            try {
                result = jdbcCall.execute(
                    idPoliza,
                    motivoEliminacion
                );
            } catch (DataAccessException e) {
                Throwable rootCause = e.getRootCause();
                if (rootCause instanceof SQLException) {
                    int errorCode = ((SQLException) rootCause).getErrorCode();

                    if (errorCode > 50000) { // Errores de negocio en el procedimiento almacenado
                        throw new PolizaDatabaseException(rootCause.getMessage());
                    }
                }
                
                throw e;
            }

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }


    private class PolizasRowMapper implements RowMapper<Poliza> {
        @Override
        public Poliza mapRow(ResultSet rs, int rowNum) throws SQLException {
            Puesto puesto = new Puesto(
                rs.getInt("IdPuestoEmpleadoGenero"),
                rs.getString("PuestoEmpleadoGenero")
            );

            Empleado empleado = new Empleado(
                rs.getInt("IdEmpleadoGenero"),
                rs.getString("NombreEmpleadoGenero"), 
                rs.getString("ApellidoEmpleadoGenero"),
                puesto
            );
            
            ProductoInventario producto = new ProductoInventario(
                rs.getString("SKU"),
                rs.getString("NombreProducto"),
                rs.getInt("Cantidad")
            );

            return new Poliza(
                rs.getInt("IdPoliza"),
                rs.getInt("IdUsuario"),
                rs.getInt("IdEmpleadoGenero"),
                empleado,
                rs.getString("SKU"),
                producto,
                rs.getInt("Cantidad"),
                rs.getString("Observaciones"),
                rs.getTimestamp("Fecha"),
                rs.getString("MotivoEliminacion"),
                rs.getTimestamp("FechaEliminacion")
            );
        }
    }
    
}
