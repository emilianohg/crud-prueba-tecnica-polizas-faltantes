package com.coppel.polizasfaltantes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.repositories.EmpleadosRepository;
import com.coppel.polizasfaltantes.repositories.mocks.MockEmpleadoRepository;

public class EmpleadosServiceTest {
    @Mock
    private EmpleadosRepository empleadosRepository;

    @InjectMocks
    private EmpleadosService empleadosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        int page = 1;
        int limit = 10;
        String search = "John";

        Pagination<Empleado> empleados = generate(limit);

        when(empleadosRepository.getAll(anyInt(), anyInt(), nullable(String.class))).thenReturn(empleados);

        Pagination<Empleado> actualPagination = empleadosService.getAll(page, limit, search);

        assertEquals(empleados, actualPagination);
    }


    private Pagination<Empleado> generate(int total) {
        MockEmpleadoRepository mockEmpleadoRepository = new MockEmpleadoRepository();
        return mockEmpleadoRepository.getAll(total);
    }
}
