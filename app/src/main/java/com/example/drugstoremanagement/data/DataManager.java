package com.example.drugstoremanagement.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.drugstoremanagement.data.db.DBHelper;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

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

    public LiveData<List<HistorySearch>> getHistorySearch() {
        return dbHelper.getHistorySearch();
    }

    public void insertHistorySearch(HistorySearch historySearch) {
        dbHelper.insertHistorySearch(historySearch);
    }

    public void deleteHistorySearch(HistorySearch historySearch) {
        dbHelper.deleteHistorySearch(historySearch);
    }

    public List<DrugStore> getDrugStore() {return dbHelper.getDrugStore();}

    public boolean insertDrugStore(DrugStore drugStore) {
        return dbHelper.insertDrugStore(drugStore) >= 0 ;
    }

    public boolean updateDrugStore(DrugStore drugStore) {
        return dbHelper.updateDrugStore(drugStore) >= 0;
    }

}
