package com.example.drugstoremanagement.data.db.model;

public class DrugStore {

    private String drugStoreID;
    private String drugStoreName;
    private String address;

    public DrugStore(String drugStoreID, String drugStoreName, String address) {
        this.drugStoreID = drugStoreID;
        this.drugStoreName = drugStoreName;
        this.address = address;
    }

    public DrugStore(DrugStore drugStore) {
        this.drugStoreID = drugStore.getDrugStoreID();
        this.drugStoreName = drugStore.getDrugStoreName();
        this.address = drugStore.getAddress();
    }

    public DrugStore(){
    }

    public String getDrugStoreID() {
        return drugStoreID;
    }

    public void setDrugStoreID(String drugStoreID) {
        this.drugStoreID = drugStoreID;
    }

    public String getDrugStoreName() {
        return drugStoreName;
    }

    public void setDrugStoreName(String drugStoreName) {
        this.drugStoreName = drugStoreName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
