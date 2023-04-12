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

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.services.InventarioService;

@ExtendWith(MockitoExtension.class)
public class InventarioControllerTest {

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController inventarioController;

    @Test
    void testIndex() {

        List<ProductoInventario> productos = new ArrayList<ProductoInventario>();

        ProductoInventario producto = new ProductoInventario(
            "SKU",
            null,
            1
        );

        productos.add(producto);

        Pagination<ProductoInventario> expectedPagination = new Pagination<ProductoInventario>(
            1,
            10,
            1,
            productos
        );

        when(inventarioService.getAll(1, 10, null))
            .thenReturn(expectedPagination);

        Pagination<ProductoInventario> actualPagination = inventarioController.index(1, 10, null);

        verify(
            this.inventarioService,
            times(1)
        ).getAll(1, 10, null);

        assertEquals(expectedPagination, actualPagination);
    }
    
}
