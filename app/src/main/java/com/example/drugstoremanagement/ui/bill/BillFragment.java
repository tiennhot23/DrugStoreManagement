package com.example.drugstoremanagement.ui.bill;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.viewmodel.DrugStoreViewModel;
import com.example.drugstoremanagement.data.viewmodel.DrugViewModel;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.example.drugstoremanagement.ui.statistic.DrugStoreNameAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String currentDrugStoreID;

    public BillFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        drugViewModel = ViewModelProviders.of(getBaseActivity(), new DrugViewModel.Factory(getContext())).get(DrugViewModel.class);
        drugViewModel.getDrugs().observe(getViewLifecycleOwner(), drugsLiveData -> {
            this.drugs.clear();
            this.drugs.addAll(drugsLiveData);
        });

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

        });
    }

    private void setupSpinner() {
        drugStoreAdapter = new DrugStoreNameAdapter(getContext(), R.layout.spinner_item, drugStores);
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(160);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentDrugStoreID = drugStoreAdapter.getItemID(i);
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
//        int n = DataManager.getInstance(getContext()).getbi().size() + 1;
//        Toast.makeText(getContext(),DataManager.getInstance(context).getDrug().size()+"X" , Toast.LENGTH_SHORT).show();
//        return String.format("D%03d", n);
        return "";
    }
}
