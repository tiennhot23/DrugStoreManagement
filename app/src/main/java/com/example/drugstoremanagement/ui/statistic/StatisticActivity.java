package com.example.drugstoremanagement.ui.statistic;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.ui.base.BaseActivity;

import java.util.List;
import java.util.Map;

public class StatisticActivity extends BaseActivity implements View.OnClickListener {

    private Spinner spinnerDrugStore;
    private ImageView btnBack;
    private ExpandableListView expandableListView;
    private ExpandableListBillAdapter adapter;
    private TextView txtTotal;

    private DrugStoreNameAdapter drugStoreAdapter;
    List<String> dates;
    List<Bill> bills;
    Map<String, List<Bill>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        setup();
    }

    @Override
    protected void setup() {
        setupView();

        drugStoreAdapter = new DrugStoreNameAdapter(this, R.layout.spinner_item, DataManager.getInstance(this).getDrugStore());
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(120);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(drugStoreAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnBack.setOnClickListener(this);
    }

    private void setupView() {
        spinnerDrugStore = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btn_back);
        expandableListView =
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                break;
            default:
                break;
        }
    }
}