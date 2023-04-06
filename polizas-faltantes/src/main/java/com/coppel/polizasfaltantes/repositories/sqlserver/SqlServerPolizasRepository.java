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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.repositories.PolizasRepository;

@Repository
public class SqlServerPolizasRepository implements PolizasRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Pagination<Poliza> getAll() {
        return this.getAll(1);
    }

    @Override
    public Pagination<Poliza> getAll(int page) {
        return this.getAll(page, 10);
    }

    @Override
    public Pagination<Poliza> getAll(int page, int limit) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("Polizas_Listar")
            .declareParameters(
                new SqlParameter("limit", Types.INTEGER),
                new SqlParameter("offset", Types.INTEGER),
                new SqlOutParameter("total", Types.INTEGER)
            )
            .returningResultSet("polizas", new PolizasRowMapper());

        int offset = ((page - 1) * limit);

        Map<String, Object> result = jdbcCall.execute(limit, offset, null);

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
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("Polizas_Registrar")
            .declareParameters(
                new SqlParameter("IdUsuario", Types.INTEGER),
                new SqlParameter("IdEmpleadoGenero", Types.INTEGER),
                new SqlParameter("SKU", Types.VARCHAR),
                new SqlParameter("Cantidad", Types.INTEGER),
                new SqlParameter("Observaciones", Types.LONGVARCHAR)
            )
            .returningResultSet("polizas", new PolizasRowMapper());


        Map<String, Object> result = jdbcCall.execute(
            polizaRequest.getIdUsuario(),
            polizaRequest.getIdEmpleadoGenero(),
            polizaRequest.getSku(),
            polizaRequest.getCantidad(),
            polizaRequest.getObservaciones()
        );

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }

    @Override
    public Optional<Poliza> update(
        int idPoliza,
        PolizaRequest polizaRequest
    ) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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


        Map<String, Object> result = jdbcCall.execute(
            idPoliza,
            polizaRequest.getIdUsuario(),
            polizaRequest.getIdEmpleadoGenero(),
            polizaRequest.getSku(),
            polizaRequest.getCantidad(),
            polizaRequest.getObservaciones()
        );

        List<Poliza> polizas = (List<Poliza>) result.get("polizas");

        return polizas.stream().findFirst();
    }


    @Override
    public Optional<Poliza> delete(
        int idPoliza,
        String motivoEliminacion
    ) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("Polizas_Eliminar")
            .declareParameters(
                new SqlParameter("IdPoliza", Types.INTEGER),
                new SqlParameter("MotivoEliminacion", Types.LONGVARCHAR)
            )
            .returningResultSet("polizas", new PolizasRowMapper());


        Map<String, Object> result = jdbcCall.execute(
            idPoliza,
            motivoEliminacion
        );

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
                rs.getDate("Fecha"),
                rs.getString("MotivoEliminacion"),
                rs.getDate("FechaEliminacion")
            );
        }
    }
    
}
