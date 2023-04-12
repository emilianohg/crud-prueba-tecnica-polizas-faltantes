package com.coppel.polizasfaltantes.repositories.mocks;

import java.util.ArrayList;
import java.util.List;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Puesto;
import com.coppel.polizasfaltantes.repositories.EmpleadosRepository;

public class MockEmpleadoRepository implements EmpleadosRepository {

    @Override
    public Pagination<Empleado> getAll() {
        return this.getAll(1);
    }

    @Override
    public Pagination<Empleado> getAll(int page) {
        return this.getAll(page, 10, null);
    }

    @Override
    public Pagination<Empleado> getAll(int page, int limit) {
        return this.getAll(page, limit, null);
    }

    @Override
    public Pagination<Empleado> getAll(int page, int limit, String buscar) {
        List<Empleado> empleados = this.generate(limit);

        return new Pagination<Empleado>(
            page,
            limit,
            empleados.size() + page * limit,
            empleados
        );
    }

    public List<Empleado> generate(int total) {
        List<Empleado> empleados = new ArrayList<Empleado>();

        for (int i = 0; i < total; i++) {
            empleados.add(
                new Empleado(
                    1,
                    "John " + i,
                    "Doe",
                    new Puesto(1, "Administrador")
                )
            );
        }

        return empleados;
    }
    
}
