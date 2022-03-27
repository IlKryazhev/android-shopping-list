package com.example.shoppinglist;

import android.app.Application;

import androidx.room.Room;

import com.example.shoppinglist.data.AppDatabase;
import com.example.shoppinglist.data.PurchaseDao;

public class App extends Application {

    private AppDatabase database;
    private PurchaseDao purchaseDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-db-name").allowMainThreadQueries().build();

        purchaseDao = database.purchaseDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public PurchaseDao getPurchaseDao() {
        return purchaseDao;
    }

    public void setPurchaseDao(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }
}
