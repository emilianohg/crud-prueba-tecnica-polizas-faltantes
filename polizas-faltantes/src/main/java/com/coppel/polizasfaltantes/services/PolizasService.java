package com.coppel.polizasfaltantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coppel.polizasfaltantes.exceptions.polizas.PolizaNotFoundException;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.repositories.PolizasRepository;


@Service
public class PolizasService {
    
    @Autowired
    private PolizasRepository polizasRepository;

    public Pagination<Poliza> getAll(int page, int limit) {
        return this.polizasRepository.getAll(page, limit);
    }

    public Poliza findById(int idPoliza) {
        Poliza poliza = this.polizasRepository.findById(idPoliza).orElseThrow(
            () -> new PolizaNotFoundException(
                String.format("No se encontró la póliza con el id %d", idPoliza)
            )
        );
        return poliza;
    }

    public Poliza store(PolizaRequest polizaRequest) {
        Poliza poliza = this.polizasRepository.store(polizaRequest)
            .orElseThrow(
                () -> new RuntimeException("Ha ocurrido un error al intentar grabar la póliza.")
            );

        return poliza;
    }

    public Poliza update(int idPoliza, PolizaRequest polizaRequest) {
        Poliza poliza = this.polizasRepository.update(idPoliza, polizaRequest)
            .orElseThrow(
                () -> new RuntimeException("Ha ocurrido un error al intentar actualizar la póliza.")
            );

        return poliza;
    }

    public Poliza delete(int idPoliza, String motivoEliminacion) {
        Poliza poliza = this.polizasRepository.delete(idPoliza, motivoEliminacion)
            .orElseThrow(
                () -> new RuntimeException("Ha ocurrido un error al intentar eliminar la póliza.")
            );

        return poliza;
    }

}
