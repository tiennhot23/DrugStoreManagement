package com.example.drugstoremanagement.data.db.model;

public class HistorySearchDrugstore {
    private String search;

    public HistorySearchDrugstore(String search) {
        this.search = search;
    }

    public HistorySearchDrugstore(){

    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
