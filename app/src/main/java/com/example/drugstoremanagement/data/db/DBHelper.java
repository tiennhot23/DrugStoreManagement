package com.example.drugstoremanagement.data.db;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.drugstoremanagement.data.db.dao.DrugDao;
import com.example.drugstoremanagement.data.db.dao.DrugStoreDao;
import com.example.drugstoremanagement.data.db.dao.HistorySearchDao;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

import java.util.List;
import java.util.concurrent.*;

public class DBHelper {

    private final int NUMBER_OF_THREADS = 4;
    private ExecutorService executor;
    private HistorySearchDao historySearchDao;
    private DrugStoreDao drugStoreDao;
    private DrugDao drugDao;

    public DBHelper(Context context) {
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        historySearchDao = AppDatabase.getInstance(context).historySearchDao();
        drugStoreDao = AppDatabase.getInstance(context).drugStoreDao();
        drugDao = AppDatabase.getInstance(context).drugDao();
    }

    public LiveData<List<HistorySearch>> getHistorySearch() {
        return historySearchDao.getAll();
    }

    public void insertHistorySearch(HistorySearch historySearch) {
        executor.execute(() -> {
            historySearchDao.insert(historySearch);
        });
    }

    public void deleteHistorySearch(HistorySearch historySearch) {
        executor.execute(() -> {
            historySearchDao.delete(historySearch);
        });
    }


    /*=============DRUGSTORE=====================*/

    public List<DrugStore> getDrugStore() {
        Future<List<DrugStore>> future = executor.submit(() -> drugStoreDao.getAll());
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("GET_DRUGSTORE", e.getMessage());
            return null;
        }
    }

    public List<DrugStore> findDrugStore(String query) {
        Future<List<DrugStore>> future = executor.submit(() -> drugStoreDao.findDrugStores(query));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("FIND_DRUGSTORE", e.getMessage());
            return null;
        }
    }

    public long insertDrugStore(DrugStore drugStore) {
        Future<Long> future = executor.submit(() -> {
            return drugStoreDao.insert(drugStore);
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("INSERT_DRUGSTORE", e.getMessage());
            return -1;
        }
    }

    public int updateDrugStore(DrugStore drugStore) {
        Future<Integer> future = executor.submit(() -> {
            return drugStoreDao.update(drugStore);
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("UPDATE_DRUGSTORE", e.getMessage());
            return -1;
        }
    }

    /*=============DRUG=====================*/

    public List<Drug> getDrug() {
        Future<List<Drug>> future = executor.submit(() -> drugDao.getAll());
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("GET_DRUG", e.getMessage());
            return null;
        }
    }

    public List<Drug> findDrug(String query) {
        Future<List<Drug>> future = executor.submit(() -> drugDao.findDrugs(query));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("FIND_DRUG", e.getMessage());
            return null;
        }
    }

    public long insertDrug(Drug drug) {
        Future<Long> future = executor.submit(() -> {
            return drugDao.insert(drug);
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("INSERT_DRUG", e.getMessage());
            return -1;
        }
    }

    public int updateDrug(Drug drug) {
        Future<Integer> future = executor.submit(() -> {
            return drugDao.update(drug);
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("UPDATE_DRUG", e.getMessage());
            return -1;
        }
    }

}
