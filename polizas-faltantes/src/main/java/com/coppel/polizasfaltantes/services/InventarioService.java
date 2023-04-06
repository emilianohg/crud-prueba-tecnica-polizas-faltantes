package com.coppel.polizasfaltantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Pagination<ProductoInventario> getAll(int page, int limit, String search) {
        return this.inventarioRepository.getAll(page, limit, search);
    }
}