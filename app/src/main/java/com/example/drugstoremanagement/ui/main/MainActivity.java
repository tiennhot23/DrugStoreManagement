package com.example.drugstoremanagement.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.base.BaseActivity;
import com.example.drugstoremanagement.ui.drugstore.DrugStoreActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btnDrugStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

    }

    @Override
    protected void setup() {
        setupView();

    }

    private void setupView() {
        btnDrugStore = findViewById(R.id.btn_drug_store);
        btnDrugStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_drug_store:
                startActivity(new Intent(this, DrugStoreActivity.class));
                break;
            default:
                break;
        }
    }
}