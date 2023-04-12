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

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.InventarioRepository;
import com.coppel.polizasfaltantes.repositories.mocks.MockInventarioRepository;

public class InventarioServiceTest {
    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        int page = 1;
        int limit = 10;
        String search = "John";

        Pagination<ProductoInventario> productos = generate(limit);

        when(inventarioRepository.getAll(anyInt(), anyInt(), nullable(String.class))).thenReturn(productos);

        Pagination<ProductoInventario> actualPagination = inventarioService.getAll(page, limit, search);

        assertEquals(productos, actualPagination);
    }


    private Pagination<ProductoInventario> generate(int total) {
        MockInventarioRepository mockInventarioRepository = new MockInventarioRepository();
        return mockInventarioRepository.getAll(total);
    }
}
