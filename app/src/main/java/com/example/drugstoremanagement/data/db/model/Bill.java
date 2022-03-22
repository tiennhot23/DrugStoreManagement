package com.example.drugstoremanagement.data.db.model;

import java.util.List;

public class Bill {
    private String billID;
    private String date;
    private String drugStoreID;
    private List<Drug> drugs;
    private long total;

    public Bill(String billID, String date, String drugStoreID, List<Drug> drugs) {
        this.billID = billID;
        this.date = date;
        this.drugStoreID = drugStoreID;
        this.drugs = drugs;
    }

    public Bill(){
    }

    public Bill(String billID, String date, long total){
        this.billID = billID;
        this.date = date;
        this.total = total;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDrugStoreID() {
        return drugStoreID;
    }

    public void setDrugStoreID(String drugStoreID) {
        this.drugStoreID = drugStoreID;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
