package com.coppel.polizasfaltantes.repositories;

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;

public interface InventarioRepository {
    public Pagination<ProductoInventario> getAll();
    public Pagination<ProductoInventario> getAll(int page);
    public Pagination<ProductoInventario> getAll(int page, int limit);
    public Pagination<ProductoInventario> getAll(int page, int limit, String buscar);
}
