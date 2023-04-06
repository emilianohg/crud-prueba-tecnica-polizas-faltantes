package com.coppel.polizasfaltantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.repositories.EmpleadosRepository;

@Service
public class EmpleadosService {

    @Autowired
    private EmpleadosRepository empleadosRepository;

    public Pagination<Empleado> getAll(int page, int limit, String search) {
        return this.empleadosRepository.getAll(page, limit, search);
    }
}
