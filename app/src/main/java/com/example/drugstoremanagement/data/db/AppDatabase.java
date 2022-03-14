package com.example.drugstoremanagement.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.drugstoremanagement.data.db.dao.HistorySearchDao;
import com.example.drugstoremanagement.data.db.model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {HistorySearch.class, DrugStore.class, Drug.class, Bill.class, DetailBill.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HistorySearchDao historySearchDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "DATABASE")
                            .build();
                }
            }
        }
        return instance;
    }
}