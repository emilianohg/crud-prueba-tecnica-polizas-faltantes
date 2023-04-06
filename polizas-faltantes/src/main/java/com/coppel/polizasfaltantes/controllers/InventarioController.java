package com.coppel.polizasfaltantes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.services.InventarioService;

@RestController
@RequestMapping("/inventario")
public class InventarioController {
    
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("")
    public Pagination<ProductoInventario> index(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        @RequestParam(value = "search", required = false) String search
    ) {
        return this.inventarioService.getAll(page, limit, search);
    }
    
}
