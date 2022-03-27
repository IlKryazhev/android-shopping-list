package com.example.shoppinglist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppinglist.model.Purchase;

import java.util.List;

@Dao
public interface PurchaseDao {

    @Query("SELECT * FROM Purchase")
    List<Purchase> getAll();

    @Query("SELECT * FROM Purchase")
    LiveData<List<Purchase>> getAllLiveData();

    @Query("SELECT * FROM Purchase WHERE uid IN (:purchaseIds)")
    List<Purchase> loadAllByIds(int[] purchaseIds);

    @Query("SELECT * FROM Purchase WHERE uid = :uid LIMIT 1")
    Purchase findById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Purchase purchase);

    @Update
    void update(Purchase purchase);

    @Delete
    void delete(Purchase purchase);
}
