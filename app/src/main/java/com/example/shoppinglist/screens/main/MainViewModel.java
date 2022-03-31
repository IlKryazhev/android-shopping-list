package com.example.shoppinglist.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.App;
import com.example.shoppinglist.model.Purchase;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Purchase>> purchaseLiveData = App.getInstance().getPurchaseDao().getAllLiveData();

    public LiveData<List<Purchase>> getPurchaseLiveData() {
        return purchaseLiveData;
    }
}
