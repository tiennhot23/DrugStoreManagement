package com.example.drugstoremanagement.ui.drugstore;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.ui.base.BaseActivity;

public class DrugStoreActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtSearch;
    private RecyclerView recycler;
    private ImageView btnAdd;
    private Dialog dialogDrugStore;

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

        dialogDrugStore = new Dialog(this);
        dialogDrugStore.setContentView(R.layout.drug_store_dialog);
        dialogDrugStore.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogDrugStore.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogDrugStore.setCancelable(false);
        dialogDrugStore.setCanceledOnTouchOutside(false);

        Button save = dialogDrugStore.findViewById(R.id.btn_save);
        save.setOnClickListener(v -> {
            dialogDrugStore.dismiss();
        });
    }

    private void setupView() {
        edtSearch = findViewById(R.id.edt_search);
        recycler = findViewById(R.id.recycler);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void search(String text) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                openDrugStoreDialog();
                break;
            default:
                break;
        }
    }

    private void openDrugStoreDialog() {
        dialogDrugStore.show();
    }
}