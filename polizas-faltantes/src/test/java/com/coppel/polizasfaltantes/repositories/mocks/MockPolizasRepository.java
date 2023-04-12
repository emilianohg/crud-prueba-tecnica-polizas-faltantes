package com.coppel.polizasfaltantes.repositories.mocks;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coppel.polizasfaltantes.models.Empleado;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.models.ProductoInventario;
import com.coppel.polizasfaltantes.repositories.PolizasRepository;

public class MockPolizasRepository implements PolizasRepository {

    @Override
    public Pagination<Poliza> getAll() {
        return this.getAll(1);
    }

    @Override
    public Pagination<Poliza> getAll(int page) {
        return this.getAll(page, 10);
    }

    @Override
    public Pagination<Poliza> getAll(int page, int limit) {
        List<Poliza> polizas = this.generate(limit);
        return new Pagination<Poliza>(
            page,
            limit,
            polizas.size() + page * limit,
            polizas
        );
    }

    @Override
    public Optional<Poliza> findById(int id) {
        return Optional.of(this.generateById(1));
    }

    @Override
    public Optional<Poliza> store(PolizaRequest polizaRequest) {
        return Optional.of(this.generateById(1));

    }

    @Override
    public Optional<Poliza> update(int idPoliza, PolizaRequest polizaRequest) {
        return Optional.of(this.generateById(idPoliza));
    }

    @Override
    public Optional<Poliza> delete(int idPoliza, String motivoEliminacion) {
        return Optional.of(this.generateById(idPoliza));
    }

    public List<Poliza> generate(int total) {
        List<Poliza> polizas = new ArrayList<Poliza>();

        for (int i = 0; i < total; i++) {
            polizas.add(
                generateById(i)
            );
        }

        return polizas;
    }

    public Poliza generateById(int id) {

        MockEmpleadoRepository mockEmpleadoRepository = new MockEmpleadoRepository();
        MockInventarioRepository mockInventarioRepository = new MockInventarioRepository();

        Empleado empleado = mockEmpleadoRepository.generate(1).get(0);
        ProductoInventario producto = mockInventarioRepository.generate(1).get(0);

        return new Poliza(
            id,
            id,
            empleado.getIdEmpleado(),
            empleado,
            producto.getSKU(),
            producto,
            10,
            "Observaciones",
            Timestamp.valueOf("2020-01-01 00:00:00"),
            null,
            null
        );
    }
    
}
