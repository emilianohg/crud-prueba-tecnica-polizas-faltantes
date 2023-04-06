package com.coppel.polizasfaltantes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.services.PolizasService;

@ExtendWith(MockitoExtension.class)
public class PolizasControllerTest {
    @Mock
    private PolizasService polizasService;

    @InjectMocks
    private PolizasController polizasController;

    @Test
    public void testIndex() {
        int page = 1;
        int limit = 10;

        List<Poliza> polizas = new ArrayList<Poliza>();

        polizas.add(PolizasControllerTest.generatePoliza());

        Pagination<Poliza> expectedPagination = new Pagination<Poliza>(
            page,
            limit,
            1,
            polizas
        );

        when(polizasService.getAll(page, limit)).thenReturn(expectedPagination);

        Pagination<Poliza> result = polizasController.index(page, limit);

        verify(
            polizasService,
            times(1)
        ).getAll(1, 10);

        assertEquals(expectedPagination, result);
    }


    @Test
    public void testShow() {
        Poliza poliza = PolizasControllerTest.generatePoliza();

        when(polizasService.findById(1)).thenReturn(poliza);

        Poliza result = polizasController.show(1);

        verify(
            polizasService,
            times(1)
        ).findById(1);

        assertEquals(poliza, result);
    }


    @Test
    public void testStore() {
        Poliza poliza = PolizasControllerTest.generatePoliza();

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            1,
            "SKU",
            1,
            "Lorem ipsum dolor sit amet"
        );

        when(polizasService.store(polizaRequest)).thenReturn(poliza);

        Poliza result = polizasController.store(polizaRequest);

        verify(
            polizasService,
            times(1)
        ).store(polizaRequest);

        assertEquals(poliza, result);
    }


    @Test
    public void testUpdate() {
        Poliza poliza = PolizasControllerTest.generatePoliza();

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            1,
            "SKU",
            1,
            "Lorem ipsum dolor sit amet"
        );

        when(polizasService.update(1, polizaRequest)).thenReturn(poliza);

        Poliza result = polizasController.update(1, polizaRequest);

        verify(
            polizasService,
            times(1)
        ).update(1, polizaRequest);

        assertEquals(poliza, result);
    }


    @Test
    public void testDelete() {
        Poliza poliza = PolizasControllerTest.generatePoliza();

        int idPoliza = 1;
        String motivoEliminacion = "Lorem ipsum dolor sit amet";

        when(polizasService.delete(idPoliza, motivoEliminacion)).thenReturn(poliza);

        Poliza result = polizasController.delete(idPoliza, motivoEliminacion);

        verify(
            polizasService,
            times(1)
        ).delete(idPoliza, motivoEliminacion);

        assertEquals(poliza, result);
    }


    public static Poliza generatePoliza() {
        return new Poliza(
            1,
            1,
            1,
            new Empleado(
                1,
                "John",
                "Doe",
                new Puesto(
                    1,
                    "Administrador"
                )
            ),
            "SKU",
            new ProductoInventario(
                "SKU",
                null,
                1
            ),
            1,
            "Lorem ipsum dolor sit amet",
            new Date(0),
            null,
            null
        );
    }
}
