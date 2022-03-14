package com.example.drugstoremanagement.data.db;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.drugstoremanagement.data.db.dao.HistorySearchDao;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBHelper {

    private final int NUMBER_OF_THREADS = 4;
    private ExecutorService executor;
    private HistorySearchDao historySearchDao;

    public DBHelper(Context context) {
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        historySearchDao = AppDatabase.getInstance(context).historySearchDao();
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

}
