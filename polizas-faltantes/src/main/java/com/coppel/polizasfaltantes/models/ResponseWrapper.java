package com.coppel.polizasfaltantes.models;

@lombok.AllArgsConstructor
@lombok.Getter
public class ResponseWrapper<T> {
    private Meta Meta;
    private T Data;
}
