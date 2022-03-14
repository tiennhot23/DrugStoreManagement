package com.example.drugstoremanagement.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Drug {
    @PrimaryKey
    @NonNull
    public String drugID;
    @NonNull
    public String drugName;
    @NonNull
    public int unit;
    @NonNull
    public int amount;
    @NonNull
    public long price;

    public Drug(String drugID, String drugName, int unit, int amount, long price) {
        this.drugID = drugID;
        this.drugName = drugName;
        this.unit = unit;
        this.amount = amount;
        this.price = price;
    }

    @Ignore
    public Drug(){
    }
}
