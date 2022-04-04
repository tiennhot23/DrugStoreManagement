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

    public LiveData<List<HistorySearchDrug>> getHistorySearchDrug() {
        return dbHelper.getHistorySearchDrug();
    }

    public void insertHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        dbHelper.insertHistorySearchDrug(historySearchDrug);
    }

    public void deleteHistorySearchDrug(HistorySearchDrug historySearchDrug) {
        dbHelper.deleteHistorySearchDrug(historySearchDrug);
    }

    public LiveData<List<HistorySearchDrugstore>> getHistorySearchDrugstore() {
        return dbHelper.getHistorySearchDrugstore();
    }

    public void insertHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        dbHelper.insertHistorySearchDrugstore(historySearchDrugstore);
    }

    public void deleteHistorySearchDrugstore(HistorySearchDrugstore historySearchDrugstore) {
        dbHelper.deleteHistorySearchDrugstore(historySearchDrugstore);
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

}
