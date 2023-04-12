package com.coppel.polizasfaltantes.repositories.mocks;

import java.util.ArrayList;
import java.util.List;

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.InventarioRepository;

public class MockInventarioRepository implements InventarioRepository {

    @Override
    public Pagination<ProductoInventario> getAll() {
        return this.getAll(1);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int page) {
        return this.getAll(page, 10, null);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int page, int limit) {
        return this.getAll(page, limit, null);
    }

    @Override
    public Pagination<ProductoInventario> getAll(int page, int limit, String buscar) {
        List<ProductoInventario> inventario = this.generate(limit);

        return new Pagination<ProductoInventario>(
            page,
            limit,
            inventario.size() + page * limit,
            inventario
        );
    }

    public List<ProductoInventario> generate(int total) {
        List<ProductoInventario> inventario = new ArrayList<ProductoInventario>();

        for (int i = 0; i < total; i++) {
            inventario.add(new ProductoInventario(
                "SKU" + i,
                "Producto " + i,
                10
            ));
        }

        return inventario;
    }
    
}
