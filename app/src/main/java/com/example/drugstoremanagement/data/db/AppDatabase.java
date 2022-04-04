package com.example.drugstoremanagement.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 2;

    public AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDrugTable = "create table if not exists Drug (" +
                " drugId text primary key, " +
                " drugName text, " +
                " unit text, " +
                " amount integer, " +
                " price integer )";
        String createDrugStoreTable = "create table if not exists DrugStore (" +
                " drugStoreId text primary key, " +
                " drugStoreName text, " +
                " address text )";
        String createBillTable = "create table if not exists Bill (" +
                " billId text primary key, " +
                " drugStoreId text, " +
                " date text, " +
                " foreign key(drugStoreId) references DrugStore(drugStoreId) " +
                " on update cascade on delete cascade )";
        String createDetailBillTable = "create table if not exists DetailBill (" +
                " billId text, " +
                " drugId text, " +
                " amount integer," +
                " primary key(billId, drugId), " +
                " foreign key(billId) references Bill(billId) " +
                " on update cascade on delete cascade, " +
                " foreign key(drugId) references Drug(drugId) " +
                " on update cascade on delete cascade )";
        String createHistorySearchDrugTable = "create table if not exists HistorySearchDrug(search text primary key)";
        String createHistorySearchDrugStoreTable = "create table if not exists HistorySearchDrugStore(search text primary key)";
        sqLiteDatabase.execSQL(createDrugTable);
        sqLiteDatabase.execSQL(createDrugStoreTable);
        sqLiteDatabase.execSQL(createBillTable);
        sqLiteDatabase.execSQL(createDetailBillTable);
        sqLiteDatabase.execSQL(createHistorySearchDrugTable);
        sqLiteDatabase.execSQL(createHistorySearchDrugStoreTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            String deleteOldTable = "drop table HistorySearch";
            sqLiteDatabase.execSQL(deleteOldTable);
            onCreate(sqLiteDatabase);
        }
    }

}