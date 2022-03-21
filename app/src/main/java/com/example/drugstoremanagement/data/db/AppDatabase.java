package com.example.drugstoremanagement.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.drugstoremanagement.data.db.dao.DrugStoreDao;
import com.example.drugstoremanagement.data.db.dao.HistorySearchDao;
import com.example.drugstoremanagement.data.db.model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 1;

    public AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public abstract HistorySearchDao historySearchDao();

    public abstract DrugStoreDao drugStoreDao();

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