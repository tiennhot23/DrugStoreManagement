package com.example.drugstoremanagement.data.db.model;


public class HistorySearchDrug {

    private String search;

    public HistorySearchDrug(String search) {
        this.search = search;
    }

    public HistorySearchDrug(){

    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
