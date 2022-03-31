package com.example.shoppinglist.screens.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shoppinglist.App;
import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Purchase;

public class PurchaseDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_PURCHASE = "PurchaseDetailsActivity.EXTRA_PURCHASE";

    Purchase purchase;

    private EditText editText;

    public static void start(Activity caller, Purchase purchase) {
        Intent intent = new Intent(caller, PurchaseDetailsActivity.class);
        if (purchase != null) {
            intent.putExtra(EXTRA_PURCHASE, purchase);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(getString(R.string.purchase_details_title));

        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_PURCHASE)) {
            purchase = getIntent().getParcelableExtra(EXTRA_PURCHASE);
            editText.setText(purchase.text);
        } else {
            purchase = new Purchase();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (editText.getText().length() > 0) {
                    purchase.text = editText.getText().toString();
                    purchase.done = false;
                    purchase.timestamp = System.currentTimeMillis();
                    if (getIntent().hasExtra(EXTRA_PURCHASE)) {
                        App.getInstance().getPurchaseDao().update(purchase);
                    } else {
                        App.getInstance().getPurchaseDao().insert(purchase);
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
