package com.example.drugstoremanagement.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;

import java.util.List;

@Dao
public interface DrugDao {
    @Query("SELECT * FROM DRUG")
    List<Drug> getAll();

    @Query("SELECT * FROM DRUG WHERE drugName LIKE '%' || :search || '%'")
    List<Drug> findDrugs(String search);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAll(Drug... drugs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Drug drug);

    @Delete
    int delete(Drug drug);

    @Update
    int update(Drug drug);
}
