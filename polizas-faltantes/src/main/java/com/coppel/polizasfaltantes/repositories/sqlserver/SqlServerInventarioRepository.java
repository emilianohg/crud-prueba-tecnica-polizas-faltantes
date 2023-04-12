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
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.InventarioRepository;

@Repository
public class SqlServerInventarioRepository implements InventarioRepository {

    public final static int DEFAULT_LIMIT = 10;

    @Autowired
    SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Pagination<ProductoInventario> getAll() {
        return this.getAll(0);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int offset) {
        return this.getAll(offset, DEFAULT_LIMIT);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int offset, int limit) {
        return this.getAll(offset, limit, null);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int page, int limit, String buscar) {

        SimpleJdbcCall jdbcCall = simpleJdbcCallFactory.create(jdbcTemplate)
            .withProcedureName("Inventario_Listar")
            .declareParameters(
                new SqlParameter("offset", Types.INTEGER),
                new SqlParameter("limit", Types.INTEGER),
                new SqlParameter("buscar", Types.VARCHAR),
                new SqlOutParameter("total", Types.INTEGER)
            )
            .returningResultSet("inventario", new ProductoInventarioRowMapper());

        int offset = ((page - 1) * limit);

        Map<String, Object> result = jdbcCall.execute(offset, limit, buscar);

        List<ProductoInventario> inventario = (List<ProductoInventario>) result.get("inventario");
        int totalRecords = (int) result.get("total");

        return new Pagination<ProductoInventario>(
            page,
            limit,
            totalRecords,
            inventario
        );
    }

    private class ProductoInventarioRowMapper implements RowMapper<ProductoInventario> {
        @Override
        public ProductoInventario mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductoInventario(
                rs.getString("SKU"),
                rs.getString("Nombre"),
                rs.getInt("Cantidad")
            );
        }
    }
    
    
}
