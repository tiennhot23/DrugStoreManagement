package com.example.drugstoremanagement.data.db.model;

public class Drug {

    private String drugID;
    private String drugName;
    private String unit;
    private int amount;
    private long price;

    public Drug(String drugID, String drugName, String unit, int amount, long price) {
        this.drugID = drugID;
        this.drugName = drugName;
        this.unit = unit;
        this.amount = amount;
        this.price = price;
    }

    public Drug(Drug drug) {
        this.drugID = drug.getDrugID();
        this.drugName = drug.getDrugName();
        this.unit = drug.getUnit();
        this.amount = drug.getAmount();
        this.price = drug.getPrice();
    }

    public Drug(){
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
