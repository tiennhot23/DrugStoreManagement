package com.example.drugstoremanagement.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(primaryKeys = {"billID", "drugID"},
        foreignKeys = {@ForeignKey(entity = Bill.class, parentColumns = "billID", childColumns = "billID"),
                        @ForeignKey(entity = Drug.class, parentColumns = "drugID", childColumns = "drugID")})
public class DetailBill {
    @NonNull
    public String billID;
    @NonNull
    public String drugID;
    @NonNull
    public int amount;

    public DetailBill(String billID, String drugID, int amount) {
        this.billID = billID;
        this.drugID = drugID;
        this.amount = amount;
    }

    @Ignore
    public DetailBill(){
    }
}
