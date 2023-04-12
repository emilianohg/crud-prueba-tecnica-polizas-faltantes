package com.coppel.polizasfaltantes.repositories;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;

public interface EmpleadosRepository {
    public Pagination<Empleado> getAll();
    public Pagination<Empleado> getAll(int page);
    public Pagination<Empleado> getAll(int page, int limit);
    public Pagination<Empleado> getAll(int page, int limit, String buscar);
}
