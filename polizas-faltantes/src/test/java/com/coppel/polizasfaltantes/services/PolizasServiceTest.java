package com.coppel.polizasfaltantes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coppel.polizasfaltantes.exceptions.polizas.PolizaNotFoundException;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.repositories.PolizasRepository;
import com.coppel.polizasfaltantes.repositories.mocks.MockPolizasRepository;

public class PolizasServiceTest {
    @Mock
    private PolizasRepository polizasRepository;

    @InjectMocks
    private PolizasService polizasService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        int page = 1, limit = 10;

        Pagination<Poliza> polizas = generate(limit);

        when(polizasRepository.getAll(anyInt(), anyInt())).thenReturn(polizas);

        Pagination<Poliza> actualPagination = polizasService.getAll(page, limit);

        assertEquals(polizas, actualPagination);
    }


    @Test
    public void testFindById__shouldReturnPoliza() {
        int idPoliza = 1;

        Optional<Poliza> poliza = generate(1).getRecords().stream().findFirst();

        when(polizasRepository.findById(anyInt())).thenReturn(poliza);

        Poliza result = polizasService.findById(idPoliza);

        assertEquals(poliza.get(), result);
    }


    @Test
    public void testFindById__shouldReturnException() {
        int idPoliza = 1;

        when(polizasRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PolizaNotFoundException.class, () -> {
            this.polizasService.findById(idPoliza);
        });
    }


    @Test
    public void testStore__shouldReturnPoliza() {
        Poliza poliza = generate(1).getRecords().get(0);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        when(polizasRepository.store(polizaRequest)).thenReturn(Optional.of(poliza));

        Poliza result = polizasService.store(polizaRequest);

        assertEquals(poliza, result);
    }


    @Test
    public void testStore__shouldReturnRuntimeException () {
        Poliza poliza = generate(1).getRecords().get(0);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        when(polizasRepository.store(polizaRequest)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            this.polizasService.store(polizaRequest);
        });
    }


    @Test
    public void testUpdate__shouldReturnPoliza() {
        int idPoliza = 1;
        Poliza poliza = generate(1).getRecords().get(0);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        when(polizasRepository.update(idPoliza, polizaRequest)).thenReturn(Optional.of(poliza));

        Poliza result = polizasService.update(idPoliza, polizaRequest);

        assertEquals(poliza, result);
    }


    @Test
    public void testUpdate__shouldReturnRuntimeException () {
        int idPoliza = 1;
        Poliza poliza = generate(1).getRecords().get(0);

        PolizaRequest polizaRequest = new PolizaRequest(
            1L,
            poliza.getIdEmpleadoGenero(),
            poliza.getSKU(),
            poliza.getCantidad(),
            poliza.getObservaciones()
        );

        when(polizasRepository.update(idPoliza, polizaRequest)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            this.polizasService.update(idPoliza, polizaRequest);
        });
    }


    @Test
    public void testDelete__shouldReturnPoliza() {
        int idPoliza = 1;
        Poliza poliza = generate(1).getRecords().get(0);
        String motivacion = "Motivacion de prueba";

        when(polizasRepository.delete(idPoliza, motivacion)).thenReturn(Optional.of(poliza));

        Poliza result = polizasService.delete(idPoliza, motivacion);

        assertEquals(poliza, result);
    }


    @Test
    public void testDelete__shouldReturnRuntimeException () {
        int idPoliza = 1;
        String motivacion = "Motivacion de prueba";

        when(polizasRepository.delete(idPoliza, motivacion)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            this.polizasService.delete(idPoliza, motivacion);
        });
    }



    private Pagination<Poliza> generate(int total) {
        MockPolizasRepository mockInventarioRepository = new MockPolizasRepository();
        return mockInventarioRepository.getAll(total);
    }
}
