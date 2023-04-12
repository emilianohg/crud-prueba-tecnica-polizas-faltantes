package com.coppel.polizasfaltantes.repositories;

import java.util.Optional;

import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;

public interface PolizasRepository {
    public Pagination<Poliza> getAll();
    public Pagination<Poliza> getAll(int page);
    public Pagination<Poliza> getAll(int page, int limit);
    public Optional<Poliza> findById(int id);
    public Optional<Poliza> store(PolizaRequest polizaRequest);
    public Optional<Poliza> update(int idPoliza, PolizaRequest polizaRequest);
    public Optional<Poliza> delete(int idPoliza, String motivoEliminacion);
}
