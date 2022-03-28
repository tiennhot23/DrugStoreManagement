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

import java.util.*;

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

        dates = new ArrayList<>();
        bills = new ArrayList<>();
        data = new HashMap<>();
        drugStoreAdapter = new DrugStoreNameAdapter(this, R.layout.spinner_item, DataManager.getInstance(this).getDrugStore());
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(120);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadData(drugStoreAdapter.getItemID(i));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        adapter = new ExpandableListBillAdapter(this, dates, data);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener((expandableListView, view, i, i1, l) -> {

            return false;
        });


        btnBack.setOnClickListener(this);
    }

    private void setupView() {
        spinnerDrugStore = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btn_back);
        expandableListView = findViewById(R.id.list_item);
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

    private void loadData(String drugStoreId) {
        bills.clear();
        bills = DataManager.getInstance(this).getBillByDrugStore(drugStoreId);
        Set<String> diffDates = new HashSet<>();
        for (Bill bill : bills) {
            diffDates.add(bill.getDate());
            if (data.get(bill.getDate()) != null) {
                List<Bill> l = new ArrayList<>();
                l.add(bill);
                data.put(bill.getDate(), l);
            } else {
                data.get(bill.getDate()).add(bill);
            }
        }
    }
}