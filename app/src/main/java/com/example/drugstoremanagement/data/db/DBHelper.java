package com.example.drugstoremanagement.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.example.drugstoremanagement.data.db.model.Drug;
import androidx.lifecycle.MutableLiveData;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrug;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrugstore;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DBHelper {

    private AppDatabase appDatabase;

    public DBHelper(Context context) {
        appDatabase = new AppDatabase(context);
    }

    @SuppressLint("Recycle")
    public List<HistorySearchDrug> getHistorySearchDrug() {
        List<HistorySearchDrug> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from HistorySearchDrug";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            HistorySearchDrug historySearch = new HistorySearchDrug(cursor.getString(0));
            data.add(historySearch);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public int insertHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        try {
            SQLiteDatabase db = appDatabase.getWritableDatabase();
            String query = "insert or ignore into HistorySearchDrug (search) values(?)";
            db.execSQL(query, new String[]{historySearchDrug.getSearch()});
            return 1;
        } catch (SQLiteException e) {
            return -1;
        }
    }

    public int deleteHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        return db.delete("HistorySearchDrug","search = ?", new String[]{historySearchDrug.getSearch()});
    }

    @SuppressLint("Recycle")
    public List<HistorySearchDrugstore> getHistorySearchDrugstore() {
        List<HistorySearchDrugstore> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select * from HistorySearchDrugstore";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            HistorySearchDrugstore historySearch = new HistorySearchDrugstore(cursor.getString(0));
            data.add(historySearch);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public int insertHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        try {
            SQLiteDatabase db = appDatabase.getWritableDatabase();
            String query = "insert or ignore into HistorySearchDrugstore (search) values(?)";
            db.execSQL(query, new String[]{historySearchDrugstore.getSearch()});
            return 1;
        } catch (SQLiteException e) {
            return -1;
        }
    }

    public int deleteHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        return db.delete("HistorySearchDrugstore","search = ?", new String[]{historySearchDrugstore.getSearch()});
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
    public DrugStore getDrugStore(String id) {

        SQLiteDatabase db = appDatabase.getReadableDatabase();
        DrugStore drugStore = null;
        String query = "select * from DrugStore WHERE DrugStoreID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        cursor.moveToFirst();
        if(!cursor.isAfterLast())
            drugStore = new DrugStore(cursor.getString(0), cursor.getString(1), cursor.getString(2));

        cursor.close();
        return drugStore;
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

    /*=============BILL=====================*/
    // lay tat ca bill(billId) hien co
    public List<Bill> getAllBill(){
        List<Bill> listBill = new ArrayList<>();
        List<String> listBillID = new ArrayList<>();
        listBillID = this.getListBillId();
        for (String id: listBillID) {
            listBill.add(this.getBill(id));
        }
        return listBill;
    }
    public List<String> getListBillId(){

        //  Log.d("vị tri",getDatabasePath(DATABASE_NAME).getPath();)
        List<String> listBillID = new ArrayList<>();
        String query="Select * from Bill  order by bill.drugStoreId , bill.date";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            listBillID.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return listBillID;
    }

    public Bill getBill(String billID){
        Bill bill = new Bill();
        DrugStore dr = new DrugStore();
        String query="SELECT * FROM BILL where BILLID = ?";
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor= db.rawQuery(query,new String[]{billID});
        cursor.moveToFirst();
        bill.setBillID(cursor.getString(0));
        bill.setDrugStore( this.getDrugStore(cursor.getString(1)));
        bill.setDate(cursor.getString(2));
        bill.setDrugs(this.getDrugOnBill(cursor.getString(0)));
        return bill;
    }
    public List<Drug> getDrugOnBill(String billID) {
        List<Drug> data = new ArrayList<>();
        SQLiteDatabase db = appDatabase.getReadableDatabase();
        String query = "select dr.drugId, dr.drugName, dr.unit, dt.amount, dr.price  from  Drug dr , detailbill dt where dr.drugId= dt.drugId and dt.billID= ?";
        Cursor cursor = db.rawQuery(query, new String[]{billID});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Drug drug = new Drug(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getLong(4));
            data.add(drug);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    /* lay chi tiet bill theo billid (billid, date, drugstore, drug, total)*/

    //them bill
    // lay danh sach bill theo nha thuoc và sap xep theo date giam dan


    public long insertBill(Bill bill) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("billId", bill.getBillID());
        values.put("drugStoreId", bill.getDrugStore().getDrugStoreID());
        values.put("date", bill.getDate());
        long result = db.insert("Bill", null, values);
        for (Drug drug : bill.getDrugs()) {
            values = new ContentValues();
            values.put("billId", bill.getBillID());
            values.put("drugId", drug.getDrugID());
            values.put("amount", drug.getAmount());
            db.insert("DetailBill", null, values);
        }
        return result;
    }



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
//        for (Bill bill : data) {
//            System.out.println(bill.getBillID());
//            System.out.println(bill.getDrugStore().getDrugStoreID());
//            System.out.println(bill.getDate());
//            for (Drug drug : bill.getDrugs()) {
//                System.out.println(drug.getDrugName());
//                System.out.println(drug.getAmount());
//            }
//        }
        return data;
    }

    
    /*=============DRUG=====================*/
    // todo: chuyển sang sqlite : done
  
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
