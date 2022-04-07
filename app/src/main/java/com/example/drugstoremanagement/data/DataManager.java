package com.example.drugstoremanagement.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.drugstoremanagement.data.db.DBHelper;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrug;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrugstore;

import java.util.List;

public class DataManager {
    private static DataManager instance;
    private DBHelper dbHelper;

    public DataManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public List<HistorySearchDrug> getHistorySearchDrug() {
        return dbHelper.getHistorySearchDrug();
    }

    public boolean insertHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        return dbHelper.insertHistorySearchDrug(historySearchDrug) >= 0;
    }

    public boolean deleteHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        return dbHelper.deleteHistorySearchDrug(historySearchDrug) >= 0;
    }

    public List<HistorySearchDrugstore> getHistorySearchDrugstore() {
        return dbHelper.getHistorySearchDrugstore();
    }

    public boolean insertHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        return dbHelper.insertHistorySearchDrugstore(historySearchDrugstore) >= 0;
    }

    public boolean deleteHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        return dbHelper.deleteHistorySearchDrugstore(historySearchDrugstore) >= 0;
    }



    public List<DrugStore> getDrugStore() {return dbHelper.getDrugStore();}

    public List<DrugStore> findDrugStore(String query) {return dbHelper.findDrugStore(query);}

    public boolean insertDrugStore(DrugStore drugStore) {
        return dbHelper.insertDrugStore(drugStore) >= 0 ;
    }

    public boolean updateDrugStore(DrugStore drugStore) {
        return dbHelper.updateDrugStore(drugStore) >= 0;
    }
  



    public List<Drug> getDrug(){
        return dbHelper.getDrug();
    }

    public List<Drug> findDrug(String query){
        return dbHelper.findDrug(query);
    }
    public boolean insertDrug(Drug drug) {
        return dbHelper.insertDrug(drug) >= 0 ;
    }

    public boolean updateDrug(Drug drug) {
        return dbHelper.updateDrug(drug) >= 0;
    }

    public boolean delete(Drug drug) {
       return dbHelper.deleteDrug(drug) >=0;
    }
  
  
  

    public List<Bill> getBillByDrugStore(String drugStoreId) {
        return dbHelper.getBillByDrugStore(drugStoreId);
    }

    public List<Bill> getAllBill() {return dbHelper.getAllBill();}

    public Bill getBill(String billId) {return dbHelper.getBill(billId);}

    public boolean insertBill(Bill bill) {return dbHelper.insertBill(bill) >= 0;}

}
