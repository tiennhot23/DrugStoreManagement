package com.example.drugstoremanagement.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

import java.util.List;

@Dao
public interface DrugStoreDao {
    @Query("SELECT * FROM DRUGSTORE")
    List<DrugStore> getAll();

    @Query("SELECT * FROM DRUGSTORE WHERE drugStoreName LIKE :search LIMIT 1")
    List<DrugStore> findDrugStores(String search);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAll(DrugStore... drugStores);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(DrugStore drugStore);

    @Delete
    int delete(DrugStore drugStore);

    @Update
    int update(DrugStore drugStore);
}
