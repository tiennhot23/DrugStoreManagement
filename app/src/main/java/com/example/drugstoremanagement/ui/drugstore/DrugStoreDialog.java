package com.example.drugstoremanagement.ui.drugstore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private Callback callback;

    private EditText edtDrugStoreName, edtAddress;
    private Button btnSave;
    private ImageView btnCancel;

    interface Callback {
        void success();
        void fail();
    }

    public DrugStoreDialog(@NonNull Context context, Callback callback, DrugStore drugStore) {
        super(context);
        this.context = context;
        this.drugStore = drugStore;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        edtDrugStoreName = findViewById(R.id.edt_drugstore_name);
        edtAddress = findViewById(R.id.edt_address);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        edtDrugStoreName.setText(drugStore.getDrugStoreName());
        edtAddress.setText(drugStore.getAddress());
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                if (drugStore.getDrugStoreID() == null || drugStore.getDrugStoreID().equals("")) {
                    if (edtDrugStoreName.getText().toString().trim().equals("")) {
                        onError(R.string.empty_drugstore_name);
                        return;
                    } if (edtAddress.getText().toString().trim().equals("")) {
                        onError(R.string.empty_address);
                        return;
                    }
                    drugStore.setDrugStoreID(generateDrugStoreID());
                    drugStore.setDrugStoreName(edtDrugStoreName.getText().toString().trim());
                    drugStore.setAddress(edtAddress.getText().toString().trim());
                    if (DataManager.getInstance(context).insertDrugStore(drugStore)) {
                        callback.success();
                    } else {
                        callback.fail();
                    }
                    dismiss();
                } else {
                    if (edtDrugStoreName.getText().toString().trim().equals("")) {
                        onError(R.string.empty_drugstore_name);
                        return;
                    } if (edtAddress.getText().toString().trim().equals("")) {
                        onError(R.string.empty_address);
                        return;
                    }
                    drugStore.setDrugStoreName(edtDrugStoreName.getText().toString().trim());
                    drugStore.setAddress(edtAddress.getText().toString().trim());
                    if (DataManager.getInstance(context).updateDrugStore(drugStore)) {
                        callback.success();
                    }
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                if (drugStore.getDrugStoreID() != null && drugStore.getDrugStoreID().equals("")) {
                    callback.fail();
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private String generateDrugStoreID() {
        int n = DataManager.getInstance(context).getDrugStore().size() + 1;
        return String.format("DS%04d", n);
    }


}
