package com.example.drugstoremanagement.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

import java.util.List;

@Dao
public interface DrugStoreDao {
    @Query("SELECT * FROM DRUGSTORE")
    LiveData<List<DrugStore>> getAll();

    @Query("SELECT * FROM DRUGSTORE WHERE drugStoreName LIKE :search LIMIT 1")
    List<DrugStore> findDrugStores(String search);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(DrugStore... drugStores);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DrugStore drugStore);

    @Delete
    void delete(DrugStore drugStore);

    @Update
    void update(DrugStore drugStore);
}
