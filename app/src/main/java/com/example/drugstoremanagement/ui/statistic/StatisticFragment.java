package com.example.drugstoremanagement.ui.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StatisticFragment extends BaseFragment implements View.OnClickListener {

    private Spinner spinnerDrugStore;
    private ExpandableListView expandableListView;
    private ExpandableListBillAdapter adapter;
    private TextView txtTotal;

    private DrugStoreNameAdapter drugStoreAdapter;
    List<String> dates;
    List<Bill> bills;
    Map<String, List<Bill>> data;

    public StatisticFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        setupView(view);

        dates = new ArrayList<>();
        bills = new ArrayList<>();
        data = new HashMap<>();
        drugStoreAdapter = new DrugStoreNameAdapter(getContext(), R.layout.spinner_item, DataManager.getInstance(getContext()).getDrugStore());
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(160);
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
        adapter = new ExpandableListBillAdapter(getContext(), dates, data);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener((expandableListView, v, i, i1, l) -> {

            return false;
        });
    }

    private void setupView(View view) {
        spinnerDrugStore = view.findViewById(R.id.spinner);
        expandableListView = view.findViewById(R.id.list_item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                break;
            default:
                break;
        }
    }

    private void loadData(String drugStoreId) {
        bills.clear();
        bills = DataManager.getInstance(getContext()).getBillByDrugStore(drugStoreId);
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
