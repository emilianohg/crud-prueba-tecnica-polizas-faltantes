package com.coppel.polizasfaltantes.repositories.sqlserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.coppel.polizasfaltantes.components.SimpleJdbcCallFactory;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.mocks.MockInventarioRepository;

public class SqlServerInventarioRepositoryTest {
    @Mock
    private SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SqlServerInventarioRepository sqlServerInventarioRepository;



    private SimpleJdbcCall mockedSimpleJdbcCall;
    

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.mockedSimpleJdbcCall = mock(SimpleJdbcCall.class);

        when(simpleJdbcCallFactory.create(any())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.withProcedureName(anyString())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlOutParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.returningResultSet(anyString(), any())).thenReturn(mockedSimpleJdbcCall);        
    }

    @Test
    public void testGetAllEmptyRecords() {
        int total = 0;

        List<ProductoInventario> records = this.generate(total).getRecords();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("inventario", records);
        expectedResult.put("total", total);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt(),
                nullable(String.class)
            )
        ).thenReturn(expectedResult);

        final Pagination<ProductoInventario> result = sqlServerInventarioRepository.getAll();

        verify(mockedSimpleJdbcCall, times(1)).execute(anyInt(), anyInt(), nullable(String.class));

        assertEquals(result.getRecords().size(), 0);
    }

    @Test
    public void testGetAllWitoutParameters() {
        int total = 10;

        List<ProductoInventario> records = this.generate(total).getRecords();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("inventario", records);
        expectedResult.put("total", total);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt(),
                nullable(String.class)
            )
        ).thenReturn(expectedResult);

        final Pagination<ProductoInventario> result = sqlServerInventarioRepository.getAll();

        verify(mockedSimpleJdbcCall, times(1)).execute(anyInt(), anyInt(), nullable(String.class));

        assertEquals(SqlServerInventarioRepository.DEFAULT_LIMIT, result.getLimit());
        assertArrayEquals(records.toArray(), result.getRecords().toArray());
    }


    @Test
    public void testGetAllWithDefaultLimitParameter() {
        final int page = 1;
        final int total = SqlServerInventarioRepository.DEFAULT_LIMIT;

        List<ProductoInventario> records = this.generate(total).getRecords();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("inventario", records);
        expectedResult.put("total", total);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt(),
                nullable(String.class)
            )
        ).thenReturn(expectedResult);

        final Pagination<ProductoInventario> result = sqlServerInventarioRepository.getAll(page);

        verify(mockedSimpleJdbcCall, times(1)).execute(anyInt(), anyInt(), nullable(String.class));

        assertEquals(SqlServerInventarioRepository.DEFAULT_LIMIT, result.getLimit());
        assertArrayEquals(records.toArray(), result.getRecords().toArray());
    }


    @Test
    public void testGetAllWithSearchParameterNull() {
        final int page = 1;
        final int limit = 10;
        final int total = 10;

        List<ProductoInventario> records = this.generate(limit).getRecords();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("inventario", records);
        expectedResult.put("total", total);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt(),
                nullable(String.class)
            )
        ).thenReturn(expectedResult);

        final Pagination<ProductoInventario> result = sqlServerInventarioRepository.getAll(page, limit);

        verify(mockedSimpleJdbcCall, times(1)).execute(anyInt(), anyInt(), nullable(String.class));

        assertArrayEquals(records.toArray(), result.getRecords().toArray());
    }

    @Test
    public void testGetAllWithAllParameters() {
        final int page = 1;
        final int limit = 10;
        final String buscar = "Hola mundo";
        final int total = 10;

        List<ProductoInventario> records = this.generate(limit).getRecords();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("inventario", records);
        expectedResult.put("total", total);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt(),
                nullable(String.class)
            )
        ).thenReturn(expectedResult);

        final Pagination<ProductoInventario> result = sqlServerInventarioRepository.getAll(page, limit, buscar);

        verify(simpleJdbcCallFactory, times(1)).create(any());
        verify(mockedSimpleJdbcCall, times(1)).withProcedureName(anyString());
        verify(mockedSimpleJdbcCall, times(1)).returningResultSet(anyString(), any());
        verify(mockedSimpleJdbcCall, times(1)).execute(anyInt(), anyInt(), nullable(String.class));

        assertArrayEquals(records.toArray(), result.getRecords().toArray());
    }

    private Pagination<ProductoInventario> generate(int total) {
        MockInventarioRepository mockInventarioRepository = new MockInventarioRepository();
        return mockInventarioRepository.getAll(1, total);
    }
}
