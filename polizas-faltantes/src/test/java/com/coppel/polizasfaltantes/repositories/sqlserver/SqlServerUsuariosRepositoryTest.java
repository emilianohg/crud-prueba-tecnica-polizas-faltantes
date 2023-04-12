package com.coppel.polizasfaltantes.repositories.sqlserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.models.UsuarioRegistroRequest;
import com.coppel.polizasfaltantes.repositories.mocks.MockUsuariosRepository;

public class SqlServerUsuariosRepositoryTest {
    @Mock
    private SimpleJdbcCallFactory simpleJdbcCallFactory;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SqlServerUsuariosRepository sqlServerUsuariosRepository;

    

    private SimpleJdbcCall mockedSimpleJdbcCall;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.mockedSimpleJdbcCall = mock(SimpleJdbcCall.class);

        when(simpleJdbcCallFactory.create(any())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.withProcedureName(anyString())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.returningResultSet(anyString(), any())).thenReturn(mockedSimpleJdbcCall);        
    }

    @Test
    public void testFindByEmail() {
        String email = "admin@example.com";

        Usuario records = this.generate(1, email);
        List<Usuario> recordsList = new ArrayList<>();
        recordsList.add(records);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Usuario> result = this.sqlServerUsuariosRepository.findByEmail(email);

        assertEquals(email, result.get().getEmail());
    }


    @Test
    public void testFindByEmail_WithEmptyResult() {

        List<Usuario> recordsList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString()
            )
        ).thenReturn(expectedResult);

        Optional<Usuario> result = this.sqlServerUsuariosRepository.findByEmail("admin@example.com");

        assertEquals(false, result.isPresent());
    }


    @Test
    public void testExistsByEmail() {
        String email = "admin@example.com";

        Usuario records = this.generate(1, email);
        List<Usuario> recordsList = new ArrayList<>();
        recordsList.add(records);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString()
            )
        ).thenReturn(expectedResult);

        boolean exist = this.sqlServerUsuariosRepository.existsByEmail(email);

        assertEquals(true, exist);
    }


    @Test
    public void testExistsByEmail_WithEmptyResult() {
        List<Usuario> recordsList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString()
            )
        ).thenReturn(expectedResult);

        boolean exist = this.sqlServerUsuariosRepository.existsByEmail("admin@example.com");

        assertEquals(false, exist);
    }


    @Test
    public void testStore() {

        UsuarioRegistroRequest request = new UsuarioRegistroRequest(
            "admin@example.com",
            "admin",
            "John",
            "Doe",
            1
        );

        Usuario records = this.generate(1, request.getEmail());
        List<Usuario> recordsList = new ArrayList<>();
        recordsList.add(records);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        when(simpleJdbcCallFactory.create(any())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.withProcedureName(anyString())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.returningResultSet(anyString(), any())).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Optional<Usuario> result = this.sqlServerUsuariosRepository.store(request);

        assertEquals(request.getEmail(), result.get().getEmail());
    }


    @Test
    public void testStore_WithEmptyResult() {
        UsuarioRegistroRequest request = new UsuarioRegistroRequest(
            "admin@example.com",
            "admin",
            "John",
            "Doe",
            1
        );

        List<Usuario> recordsList = new ArrayList<>();

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("usuarios", recordsList);

        this.mockedSimpleJdbcCall = mock(SimpleJdbcCall.class);

        when(simpleJdbcCallFactory.create(any())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.withProcedureName(anyString())).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.declareParameters(
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class),
            any(SqlParameter.class)
        )).thenReturn(mockedSimpleJdbcCall);
        when(mockedSimpleJdbcCall.returningResultSet(anyString(), any())).thenReturn(mockedSimpleJdbcCall);

        when(
            this.mockedSimpleJdbcCall.execute(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyInt()
            )
        ).thenReturn(expectedResult);

        Optional<Usuario> result = this.sqlServerUsuariosRepository.store(request);

        assertEquals(false, result.isPresent());
    }


    private Usuario generate(int id, String email) {
        MockUsuariosRepository mockUsuariosRepository = new MockUsuariosRepository();
        return mockUsuariosRepository.generate(id, email);
    }
        
}
