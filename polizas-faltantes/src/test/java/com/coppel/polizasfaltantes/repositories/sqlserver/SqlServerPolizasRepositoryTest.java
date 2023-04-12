package com.coppel.polizasfaltantes.repositories.sqlserver;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.repositories.mocks.MockPolizasRepository;

public class SqlServerPolizasRepositoryTest {
    @Mock
    private SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SqlServerPolizasRepository sqlServerPolizasRepository;

    

    private SimpleJdbcCall mockedSimpleJdbcCall;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.mockedSimpleJdbcCall = mock(SimpleJdbcCall.class);

        when(simpleJdbcCallFactory.create(any())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.withProcedureName(anyString())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.returningResultSet(anyString(), any())).thenReturn(mockedSimpleJdbcCall);        
    }

    @Test
    public void testGetAll__shouldReturnAllPolizas() {
        Poliza poliza = generateById(1);
        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas", polizasList);
        expectedResult.put("total", 1);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlOutParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Pagination<Poliza> actualResult = this.sqlServerPolizasRepository.getAll(1, 10);
        
        assertArrayEquals(polizasList.toArray(), actualResult.getRecords().toArray());
    }


    @Test
    public void testGetAll__shouldReturnEmptyList() {
        List<Poliza> polizasList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas", polizasList);
        expectedResult.put("total", 1);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlOutParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Pagination<Poliza> actualResult = this.sqlServerPolizasRepository.getAll(1, 10);
        
        assertEquals(0, actualResult.getRecords().size());
    }


    @Test
    public void testGetAllWithDefaultLimit() {
        Poliza poliza = generateById(1);
        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas", polizasList);
        expectedResult.put("total", 1);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlOutParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Pagination<Poliza> actualResult = this.sqlServerPolizasRepository.getAll(1);

        assertEquals(SqlServerPolizasRepository.DEFAULT_LIMIT, actualResult.getLimit());
        assertArrayEquals(polizasList.toArray(), actualResult.getRecords().toArray());
    }


    @Test
    public void testGetAllWithoutParameters() {
        Poliza poliza = generateById(1);
        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas", polizasList);
        expectedResult.put("total", 1);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlOutParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Pagination<Poliza> actualResult = this.sqlServerPolizasRepository.getAll();

        assertEquals(SqlServerPolizasRepository.DEFAULT_LIMIT, actualResult.getLimit());
        assertArrayEquals(polizasList.toArray(), actualResult.getRecords().toArray());
    }

    @Test
    public void testFindById__shouldReturnPoliza() {
        Poliza poliza = generateById(1);
        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);


        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.findById(1);

        assertEquals(poliza, actualResult.get());
    }


    @Test
    public void testFindById__shouldReturnEmptyOptional() {
        List<Poliza> polizasList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);


        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.findById(1);

        assertEquals(Optional.empty(), actualResult);
    }

    @Test
    public void store__shouldReturnPoliza() {
        Poliza poliza = generateById(1);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class))
        ).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyLong(),
                anyInt(),
                anyString(),
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.store(polizaRequest);

        assertEquals(poliza, actualResult.get());
    }


    @Test
    public void store__shouldReturnEmptyOptional() {
        Poliza poliza = generateById(1);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        List<Poliza> polizasList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class))
        ).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyLong(),
                anyInt(),
                anyString(),
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.store(polizaRequest);

        assertEquals(Optional.empty(), actualResult);
    }


    @Test
    public void update__shouldReturnPoliza() {
        int idPoliza = 1;
        Poliza poliza = generateById(idPoliza);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class))
        ).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyLong(),
                anyInt(),
                anyString(),
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.update(idPoliza, polizaRequest);

        assertEquals(poliza, actualResult.get());
    }


    @Test
    public void update__shouldReturnEmptyOptional() {
        int idPoliza = 1;
        Poliza poliza = generateById(idPoliza);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        List<Poliza> polizasList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class))
        ).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyLong(),
                anyInt(),
                anyString(),
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.update(idPoliza, polizaRequest);

        assertEquals(Optional.empty(), actualResult);
    }


    @Test
    public void testDelete__shouldReturnPoliza() {
        Poliza poliza = generateById(1);

        List<Poliza> polizasList = new ArrayList<>();
        polizasList.add(poliza);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.delete(
            poliza.getIdPoliza(),
            poliza.getObservaciones()
        );

        assertEquals(poliza, actualResult.get());
    }


    @Test
    public void testDelete__shouldReturnEmptyOptional() {
        Poliza poliza = generateById(1);

        List<Poliza> polizasList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("polizas",         polizasList);

        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyInt(),
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Poliza> actualResult = this.sqlServerPolizasRepository.delete(
            poliza.getIdPoliza(),
            poliza.getObservaciones()
        );

        assertEquals(Optional.empty(), actualResult);
    }


    private Poliza generateById(int id) {
        MockPolizasRepository mockPolizasRepository = new MockPolizasRepository();
        return mockPolizasRepository.generateById(id);
    }
    
}
