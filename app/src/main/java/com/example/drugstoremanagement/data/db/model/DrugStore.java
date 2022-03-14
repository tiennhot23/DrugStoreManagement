package com.example.drugstoremanagement.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DrugStore {
    @PrimaryKey
    @NonNull
    public String drugStoreID;
    @NonNull
    public String drugStoreName;
    @NonNull
    public String address;

    public DrugStore(String drugStoreID, String drugStoreName, String address) {
        this.drugStoreID = drugStoreID;
        this.drugStoreName = drugStoreName;
        this.address = address;
    }

    @Ignore
    public DrugStore(){
    }
}
