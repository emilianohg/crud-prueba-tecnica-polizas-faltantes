package com.coppel.polizasfaltantes.models;

import com.fasterxml.jackson.annotation.JsonCreator;

@lombok.NoArgsConstructor
@lombok.Getter
public class EliminacionPolizaRequest {
    
    private String motivoEliminacion;

    @JsonCreator
    public EliminacionPolizaRequest(String motivoEliminacion) {
        this.motivoEliminacion = motivoEliminacion;
    }

}
