package com.example.drugstoremanagement.ui.statistic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.viewmodel.DrugStoreViewModel;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.example.drugstoremanagement.ui.detailbill.DetailBillActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StatisticFragment extends BaseFragment {

    private Spinner spinnerDrugStore;
    private ExpandableListView expandableListView;
    private ExpandableListBillAdapter adapter;
    private TextView txtTotal;
    private Switch btnSwitch;
    private BarChart chart;

    private DrugStoreNameAdapter drugStoreAdapter;
    List<String> dates;
    List<Bill> bills;
    Map<String, List<Bill>> data;

    private List<DrugStore> drugStores = new ArrayList<>();

    private DrugStoreViewModel drugStoreViewModel;

    public StatisticFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        dates = new ArrayList<>();
        bills = new ArrayList<>();
        data = new HashMap<>();

        setupView(view);
        setupSpinner();

        adapter = new ExpandableListBillAdapter(getContext(), dates, data);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener((expandableListView, v, i, i1, l) -> {
            Intent intent = new Intent(getBaseActivity(), DetailBillActivity.class);
            intent.putExtra("billId", adapter.getChild(i, i1).getBillID());
            startActivity(intent);
            return false;
        });
        btnSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                expandableListView.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
            } else {
                expandableListView.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
            }
        });
    }

    private void setupView(View view) {
        spinnerDrugStore = view.findViewById(R.id.spinner);
        expandableListView = view.findViewById(R.id.list_item);
        txtTotal = view.findViewById(R.id.txt_total);
        btnSwitch = view.findViewById(R.id.btn_switch);
        chart = view.findViewById(R.id.chart);
    }

    private void setupSpinner() {
        drugStoreAdapter = new DrugStoreNameAdapter(getContext(), R.layout.spinner_item, R.id.txt_drugstore_name, drugStores);
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(160);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadData(drugStoreAdapter.getItemID(i));
                loadChart();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        drugStoreViewModel = ViewModelProviders.of(getBaseActivity(), new DrugStoreViewModel.Factory(getContext())).get(DrugStoreViewModel.class);
        drugStoreViewModel.getDrugstores().observe(getViewLifecycleOwner(), drugStoresLiveData -> {
            this.drugStores.clear();
            this.drugStores.addAll(drugStoresLiveData);
            if (drugStoreAdapter != null) drugStoreAdapter.notifyDataSetChanged();
        });
    }

    private void loadData(String drugStoreId) {
        bills.clear();
        dates.clear();
        data.clear();
        long total = 0;
        bills = DataManager.getInstance(getContext()).getBillByDrugStore(drugStoreId);
        Set<String> diffDates = new HashSet<>();
        for (Bill bill : bills) {
            total += bill.getTotal();
            diffDates.add(bill.getDate());
            if (data.get(bill.getDate()) == null) {
                List<Bill> l = new ArrayList<>();
                l.add(bill);
                data.put(bill.getDate(), l);
            } else {
                data.get(bill.getDate()).add(bill);
            }
        }
        dates.addAll(diffDates);
        txtTotal.setText(String.valueOf(total));
    }

    private void loadChart() {
        ArrayList<BarEntry> income = new ArrayList<>();
        for (int i=0; i<dates.size(); i++) {
            int total = 0;
            for (Bill bill : Objects.requireNonNull(data.get(dates.get(i)))) {
                total += bill.getTotal();
            }
            income.add(new BarEntry(i, total));
        }
        chart.getXAxis().setXOffset(1f);
        chart.getXAxis().setAxisMaximum(dates.size());

        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int val = (int) value;
                if (val == 0) {
                    return dates.get(val);
                } else return "";
            }
        });


        BarDataSet barDataSet = new BarDataSet(income, "Doanh thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        chart.setFitBars(true);
        chart.setData(barData);
        chart.animateY(2000);

    }
}
