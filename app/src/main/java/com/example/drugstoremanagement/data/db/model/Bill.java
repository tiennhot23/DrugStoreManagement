package com.example.drugstoremanagement.data.db.model;

import java.util.List;

public class Bill {
    private String billID;
    private String date;
    private String drugStoreID;
    private List<Drug> drugs;

    public Bill(String billID, String date, String drugStoreID, String total, List<Drug> drugs) {
        this.billID = billID;
        this.date = date;
        this.drugStoreID = drugStoreID;
        this.drugs = drugs;
    }

    public Bill(){
    }
}
