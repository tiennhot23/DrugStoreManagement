package com.example.drugstoremanagement.ui.drug;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.ui.base.BaseDialog;

public class DrugDialog extends BaseDialog implements View.OnClickListener{
    Context context;
    Callback callback;
    Drug drug;

    EditText edt_drug_name,edt_amount,edt_unit,edt_price;
    Button btnSave;
    ImageView btnCancel;

    public DrugDialog(@NonNull Context context,Callback callback, Drug drug) {
        super(context);
        this.context = context;
        this.callback = callback;
        this.drug = drug;
    }

    interface Callback {
        void success();
        void fail();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        edt_drug_name = findViewById(R.id.edt_drug_name);
        edt_amount = findViewById(R.id.edt_amount);
       //edt_unit = findViewById(R.id.edt_unit);
        edt_price = findViewById(R.id.edt_price);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        edt_drug_name.setText(drug.drugName);
//        edt_unit.setText(drug.unit+"");
        edt_amount.setText(drug.amount+"");
        edt_price.setText(drug.price+"");
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSave:
                if (drug.drugID == null || drug.drugID.equals("")) {
                    if (edt_drug_name.getText().toString().trim().equals("")) {
                        onError(R.string.empty_drugstore_name);
                        return;
                    } if (edt_amount.getText().toString().trim().equals("")) {
                        onError(R.string.empty_amount);
                        return;
                    }

                    if (edt_price.getText().toString().trim().equals("")) {
                        onError(R.string.empty_price);
                        return;
                    }
                    drug.drugID = generateDrugStoreID();
                   drug.drugName = edt_drug_name.getText().toString().trim();
                    drug.amount = Integer.parseInt(edt_amount.getText().toString().trim());
                    drug.unit = 0;
                    drug.price = Integer.parseInt(edt_price.getText().toString().trim());

                    if (DataManager.getInstance(context).insertDrug(drug)) {
                        callback.success();
                    } else {
                        callback.fail();
                    }
                    dismiss();
                } else {
                    if (edt_drug_name.getText().toString().trim().equals("")) {
                        onError(R.string.empty_drugstore_name);
                        return;
                    } if (edt_amount.getText().toString().trim().equals("")) {
                        onError(R.string.empty_amount);
                        return;
                    }
                    if (edt_price.getText().toString().trim().equals("")) {
                        onError(R.string.empty_price);
                        return;
                    }
                    drug.drugName = edt_drug_name.getText().toString().trim();
                    drug.amount = Integer.parseInt(edt_amount.getText().toString().trim());
                    drug.unit = 0;
                    drug.price = Integer.parseInt(edt_price.getText().toString().trim());
                    if (DataManager.getInstance(context).updateDrug(drug)) {
                        callback.success();
                    }
                    dismiss();
                }
                break;
            case R.id.btnCancel:
                if (drug.drugID != null && drug.drugID.equals("")) {
                    callback.fail();
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void setup() {
        setContentView(R.layout.drug_dialog);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.ZoomAnimation;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
    @SuppressLint("DefaultLocale")
    private String generateDrugStoreID() {
        int n = DataManager.getInstance(context).getDrug().size() + 1;
        return String.format("DS%04d", n);
    }
}
