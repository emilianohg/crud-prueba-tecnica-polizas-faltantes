package com.coppel.polizasfaltantes.repositories.sqlserver;

import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.coppel.polizasfaltantes.components.SimpleJdbcCallFactory;
import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.repositories.EmpleadosRepository;

@Repository
public class SqlServerEmpleadosRepository implements EmpleadosRepository {

    public final static int DEFAULT_LIMIT = 10;

    @Autowired
    SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Pagination<Empleado> getAll() {
        return this.getAll(0);
    }

    @Override
    public Pagination<Empleado> getAll(int offset) {
        return this.getAll(offset, DEFAULT_LIMIT);
    }


    @Override
    public Pagination<Empleado> getAll(int offset, int limit) {
        return this.getAll(offset, limit, null);
    }

    @Override
    public Pagination<Empleado> getAll(int page, int limit, String buscar) {
        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Empleados_Listar")
            .declareParameters(
                new SqlParameter("offset", Types.INTEGER),
                new SqlParameter("limit", Types.INTEGER),
                new SqlParameter("buscar", Types.VARCHAR),
                new SqlOutParameter("total", Types.INTEGER)
            )
            .returningResultSet("empleados", new EmpleadoRowMapper());

        int offset = ((page - 1) * limit);

        Map<String, Object> result = jdbcCall.execute(offset, limit, buscar);

        List<Empleado> empleados = (List<Empleado>) result.get("empleados");
        int totalRecords = (int) result.get("total");

        return new Pagination<Empleado>(
            page,
            limit,
            totalRecords,
            empleados
        );
    }

    private class EmpleadoRowMapper implements RowMapper<Empleado> {
        @Override
        public Empleado mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Empleado(
                rs.getInt("IdEmpleado"),
                rs.getString("Nombre"),
                rs.getString("Apellido"),
                new Puesto(
                    rs.getInt("IdPuesto"),
                    rs.getString("Puesto")
                )
            );
        }
    }
    
    
}
