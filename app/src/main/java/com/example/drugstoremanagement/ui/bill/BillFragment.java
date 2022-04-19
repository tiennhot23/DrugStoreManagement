package com.example.drugstoremanagement.ui.bill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.viewmodel.DrugStoreViewModel;
import com.example.drugstoremanagement.data.viewmodel.DrugViewModel;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.example.drugstoremanagement.ui.detailbill.DetailBillActivity;
import com.example.drugstoremanagement.ui.statistic.DrugStoreNameAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BillFragment extends BaseFragment implements DrugInBillDialog.Callback{

    private List<Drug> drugs;
    private List<Drug> drugsSelected;
    private List<DrugStore> drugStores;
    private DrugStoreNameAdapter drugStoreAdapter;
    private DetailBillAdapter detailBillAdapter;
    private DrugStoreViewModel drugStoreViewModel;
    private DrugViewModel drugViewModel;

    private Spinner spinnerDrugStore;
    private TextView txtTotal, txtBillId, txtDate;
    private ListView listItem;
    private Button btnCreateBill;
    private ImageButton btnAdd;

    private int currentDrugStorePosition;

    public BillFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        drugs = new ArrayList<>();
        drugsSelected = new ArrayList<>();
        drugStores = new ArrayList<>();
        setupView(view);
        setupEvent();
        setupSpinner();

        detailBillAdapter = new DetailBillAdapter(getContext(), R.layout.item_detail_bill, drugsSelected);
        listItem.setAdapter(detailBillAdapter);
        drugs = DataManager.getInstance(getContext()).getDrug();

        initData();
    }

    private void setupView(View view) {
        spinnerDrugStore = view.findViewById(R.id.spinner);
        txtTotal = view.findViewById(R.id.txt_total);
        txtDate = view.findViewById(R.id.txt_date);
        txtBillId = view.findViewById(R.id.txt_bill_id);
        listItem = view.findViewById(R.id.list_item);
        btnCreateBill = view.findViewById(R.id.btn_create_bill);
        btnAdd = view.findViewById(R.id.btn_add);
    }

    private void setupEvent() {
        btnAdd.setOnClickListener(v -> {
            DrugInBillDialog drugInBillDialog = new DrugInBillDialog(getContext(), drugs, drugsSelected, this);
            drugInBillDialog.show();
        });
        btnCreateBill.setOnClickListener(v -> {
            createBill();
        });
    }

    private void createBill() {
        if (drugsSelected.size() > 0) {
            Bill bill = new Bill();
            bill.setBillID(txtBillId.getText().toString().trim());
            bill.setDate(txtDate.getText().toString().trim());
            bill.setDrugStore(drugStores.get(currentDrugStorePosition));
            bill.setDrugs(drugsSelected);
            if (DataManager.getInstance(getContext()).insertBill(bill)) {
                showPopupMessage("Tạo hóa đơn thành công", R.raw.success);
                resetData();

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(getBaseActivity(), DetailBillActivity.class);
                    intent.putExtra("billId", bill.getBillID());
                    startActivity(intent);
                }, 2000);

            } else showPopupMessage("Tạo hóa đơn thất bại", R.raw.error);
        }
    }

    private void setupSpinner() {
        drugStoreAdapter = new DrugStoreNameAdapter(getContext(), R.layout.spinner_item, R.id.txt_drugstore_name, drugStores);
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(160);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentDrugStorePosition = i;
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

    @SuppressLint("SimpleDateFormat")
    private void initData() {
        int total = 0;
        for (Drug drug : drugsSelected) {
            total += drug.getAmount() * drug.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        txtBillId.setText(generateBillId());
    }

    @SuppressLint("SimpleDateFormat")
    private void resetData() {
        drugsSelected.clear();
        if (detailBillAdapter != null) detailBillAdapter.notifyDataSetChanged();
        initData();
    }

    @Override
    public void onButtonSaveClick() {
        if (detailBillAdapter != null) detailBillAdapter.notifyDataSetChanged();
        int total = 0;
        for (Drug drug : drugsSelected) {
            total += drug.getAmount() * drug.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
    }

    @SuppressLint("DefaultLocale")
    private String generateBillId() {
        int n = DataManager.getInstance(getContext()).getAllBill().size() + 1;
        return String.format("HD%03d", n);
    }


}
