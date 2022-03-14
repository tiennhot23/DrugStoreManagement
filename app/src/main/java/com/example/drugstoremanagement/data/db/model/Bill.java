package com.example.drugstoremanagement.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = DrugStore.class, parentColumns = "drugStoreID", childColumns = "drugStoreID")})
public class Bill {
    @PrimaryKey
    @NonNull
    public String billID;
    @NonNull
    public String date;
    @NonNull
    public String drugStoreID;

    public Bill(String billID, String date, String drugStoreID) {
        this.billID = billID;
        this.date = date;
        this.drugStoreID = drugStoreID;
    }

    @Ignore
    public Bill(){
    }
}
