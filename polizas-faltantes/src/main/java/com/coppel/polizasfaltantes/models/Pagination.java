package com.coppel.polizasfaltantes.models;

import java.util.List;

@lombok.Getter
public class Pagination<T> {
    private int Page;
    private int Limit;
    private int TotalRecords;
    private List<T> Records;

    public Pagination(int page, int limit, int totalRecords, List<T> records) {
        Page = page;
        Limit = limit;
        TotalRecords = totalRecords;
        Records = records;
    }

    public int getCurrentPage() {
        return Page;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) TotalRecords / Limit);
    }
}
