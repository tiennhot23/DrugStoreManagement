package com.example.drugstoremanagement.ui.drugstore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.ui.base.BaseActivity;
import com.example.drugstoremanagement.ui.base.BaseDialog;
import com.google.android.material.snackbar.Snackbar;

public class DrugStoreDialog extends BaseDialog implements View.OnClickListener {

    private DrugStore drugStore;
    private Context context;

    private EditText edtDrugStoreName, edtAddress;
    private Button btnSave;

    public DrugStoreDialog(@NonNull Context context, DrugStore drugStore) {
        super(context);
        this.context = context;
        this.drugStore = drugStore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        edtDrugStoreName = findViewById(R.id.edt_drugstore_name);
        edtAddress = findViewById(R.id.edt_address);
        btnSave = findViewById(R.id.btn_save);

        edtDrugStoreName.setText(drugStore.drugStoreName);
        edtAddress.setText(drugStore.address);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void setup() {
        setContentView(R.layout.drug_store_dialog);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.ZoomAnimation;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            if (drugStore.drugStoreID == null || drugStore.drugStoreID.equals("")) {
                if (edtDrugStoreName.getText().toString().trim().equals("")) {
                    onError(R.string.empty_drugstore_name);
                    return;
                } if (edtAddress.getText().toString().trim().equals("")) {
                    onError(R.string.empty_address);
                    return;
                }
                drugStore.drugStoreID = generateDrugStoreID();
                drugStore.drugStoreName = edtDrugStoreName.getText().toString().trim();
                drugStore.address = edtAddress.getText().toString().trim();
                if (DataManager.getInstance(context).insertDrugStore(drugStore)) {
                    showMessage(R.string.save_success);
                } else {
                    showMessage(R.string.save_fail);
                }
                dismiss();
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String generateDrugStoreID() {
        int n = DataManager.getInstance(context).getDrugStore().size() + 1;
        return String.format("DS%04d", n);
    }


}
