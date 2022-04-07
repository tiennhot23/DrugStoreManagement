package com.example.drugstoremanagement.data.db.model;

import java.util.List;

public class Bill {
    private String billID;
    private String date;
    private DrugStore drugStore;
    private List<Drug> drugs;
    private long total;

    public Bill(String billID, String date, DrugStore drugStore, List<Drug> drugs) {
        this.billID = billID;
        this.date = date;
        this.drugStore = drugStore;
        this.drugs = drugs;
    }

    public Bill(){
    }

    public Bill(String billID, String date, long total){
        this.billID = billID;
        this.date = date;
        this.total = total;
    }

    public Bill(String billID, DrugStore drugStore, String date){
        this.billID = billID;
        this.date = date;
        this.drugStore = drugStore;
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

    public DrugStore getDrugStore() {
        return drugStore;
    }

    public void setDrugStore(DrugStore drugStore) {
        this.drugStore = drugStore;
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

    public long calTotal() {
        long total = 0;
        for (Drug drug : drugs) {
            total += drug.getAmount() * drug.getPrice();
        }
        return total;
    }
}
