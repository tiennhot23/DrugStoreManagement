package com.example.drugstoremanagement.ui.drugstore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.ui.base.BaseActivity;

import java.util.List;

public class DrugStoreActivity extends BaseActivity implements View.OnClickListener, DrugStoreDialog.Callback, RecyclerListener {

    private EditText edtSearch;
    private RecyclerView recycler;
    private ImageView btnAdd, btnBack, btnSearch;
    private DrugStoreDialog dialogDrugStore;

    private List<DrugStore> drugStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_store);

        setup();
    }

    @Override
    protected void setup() {
        setupView();
        edtSearch.setOnKeyListener((view, i, keyEvent) -> {
            search(edtSearch.getText().toString().trim());
            return true;
        });
        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        drugStores = DataManager.getInstance(this).getDrugStore();

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new DrugStoreAdapter(this, this, drugStores));
    }

    private void setupView() {
        edtSearch = findViewById(R.id.edt_search);
        recycler = findViewById(R.id.recycler);
        btnAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_search);
    }

    private void search(String text) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                addDrugStore();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                search(edtSearch.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void addDrugStore() {
        DrugStore drugStore = new DrugStore();
        drugStores.add(drugStore);
        dialogDrugStore = new DrugStoreDialog(this, this, drugStore);
        dialogDrugStore.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void success() {
        showMessage(R.string.save_success);
        if (recycler.getAdapter() != null) recycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void fail() {
        showMessage(R.string.save_fail);
        if (drugStores != null && drugStores.size() > 0) {
            drugStores.remove(drugStores.size() - 1);
        }
    }

    @Override
    public void onItemClick(int position) {
        dialogDrugStore = new DrugStoreDialog(this, this, drugStores.get(position));
        dialogDrugStore.show();
    }
}