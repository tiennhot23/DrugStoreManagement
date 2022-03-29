package com.example.drugstoremanagement.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.drugstoremanagement.data.db.model.Drug;
import androidx.lifecycle.MutableLiveData;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DBHelper {

    private AppDatabase appDatabase;

    public DBHelper(Context context) {
        appDatabase = new AppDatabase(context);
    }

    @SuppressLint("Recycle")
    public LiveData<List<HistorySearch>> getHistorySearch() {
        MutableLiveData<List<HistorySearch>> liveData = new MutableLiveData<>();
        List<HistorySearch> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from HistorySearch";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            HistorySearch historySearch = new HistorySearch(cursor.getString(0));
            data.add(historySearch);
            cursor.moveToNext();
        }
        cursor.close();
        liveData.postValue(data);
        return liveData;
    }

    public void insertHistorySearch(HistorySearch historySearch) {

    }

    public void deleteHistorySearch(HistorySearch historySearch) {

    }


    /*=============DRUGSTORE=====================*/

    public List<DrugStore> getDrugStore() {
        List<DrugStore> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from DrugStore";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            DrugStore drugStore = new DrugStore(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            data.add(drugStore);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public List<DrugStore> findDrugStore(String s) {
        List<DrugStore> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from DrugStore where drugStoreName like ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + s + "%"});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            DrugStore drugStore = new DrugStore(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            data.add(drugStore);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public long insertDrugStore(DrugStore drugStore) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("drugStoreId", drugStore.getDrugStoreID());
        values.put("drugStoreName", drugStore.getDrugStoreName());
        values.put("address", drugStore.getAddress());
        return db.insert("DrugStore", null, values);
    }

    public long updateDrugStore(DrugStore drugStore) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("drugStoreId", drugStore.getDrugStoreID());
        values.put("drugStoreName", drugStore.getDrugStoreName());
        values.put("address", drugStore.getAddress());
        return db.update("DrugStore", values, "drugStoreId = ?", new String[]{drugStore.getDrugStoreID()});
    }

    /*=============DRUG=====================*/


    /*=============STATISTIC=====================*/
    public List<Bill> getBillByDrugStore(String drugStoreId) {
        List<Bill> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select b.billId, date, total from bill b, " +
                "(select  b.billId, sum(d.price * db.amount) as total " +
                "from Bill b, DetailBill db, Drug d " +
                "where b.billId = db.billId and db.drugId = d.drugId and b.drugStoreId = ?" +
                "group by b.billId) db " +
                "where b.billId = db.billId " +
                "order by b.date ";
        Cursor cursor = db.rawQuery(query, new String[]{drugStoreId});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Bill bill = new Bill(cursor.getString(0), cursor.getString(1), cursor.getLong(2));
            data.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    
    /*=============DRUG=====================*/
    // todo: chuyển sang sqlite
  
    public List<Drug> getDrug() {
        List<Drug> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from Drug";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Drug drug = new Drug(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getLong(4));
            data.add(drug);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public List<Drug> findDrug(String query) {
        List<Drug> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String sql = "select * from Drug where drugName LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query + "%"});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Drug drug = new Drug(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getLong(4));
            data.add(drug);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public long insertDrug(Drug drug) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("drugId", drug.getDrugID());
        values.put("drugName", drug.getDrugName());
        values.put("unit", drug.getUnit());
        values.put("amount", drug.getAmount());
        values.put("price", drug.getPrice());
        return db.insert("Drug", null, values);
    }

    public int updateDrug(Drug drug) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("drugId", drug.getDrugID());
        values.put("drugName", drug.getDrugName());
        values.put("unit", drug.getUnit());
        values.put("amount", drug.getAmount());
        values.put("price", drug.getPrice());
        return db.update("Drug", values, "drugId = ?", new String[]{drug.getDrugID()});
    }

    public int deleteDrug(Drug drug) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        return db.delete("Drug","drugId = ?", new String[]{drug.getDrugID()});
    }

}
