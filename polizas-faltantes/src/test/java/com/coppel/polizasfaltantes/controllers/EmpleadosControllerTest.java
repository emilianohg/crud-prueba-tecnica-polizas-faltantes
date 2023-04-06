package com.coppel.polizasfaltantes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.services.EmpleadosService;

@ExtendWith(MockitoExtension.class)
public class EmpleadosControllerTest {

    @Mock
    private EmpleadosService empleadosService;

    @InjectMocks
    private EmpleadosController empleadosController;

    @Test
    void testIndex() {

        List<Empleado> empleados = new ArrayList<Empleado>();

        Empleado empleado = new Empleado(
            1,
            "John",
            "Doe",
            new Puesto(
                1,
                "Administrador"
            )
        );

        empleados.add(empleado);

        Pagination<Empleado> expectedPagination = new Pagination<Empleado>(
            1,
            10,
            1,
            empleados
        );

        when(empleadosService.getAll(1, 10, null))
            .thenReturn(expectedPagination);

        Pagination<Empleado> actualPagination = empleadosController.index(1, 10, null);

        verify(
            empleadosService,
            times(1)
        ).getAll(1, 10, null);

        assertEquals(expectedPagination, actualPagination);
    }
    
}
