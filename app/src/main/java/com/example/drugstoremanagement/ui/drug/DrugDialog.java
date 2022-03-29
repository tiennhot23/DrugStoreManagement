package com.example.drugstoremanagement.ui.drug;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.AppDatabase;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class DrugDialog extends BaseDialog implements View.OnClickListener{
    Context context;
    Callback callback;
    Drug drug;

    EditText edt_drug_name,edt_amount,edt_price;
    Spinner spinner;
    Button btnSave;
    ImageView btnCancel;
    List<String> listUnit = new ArrayList<>();

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
       spinner = findViewById(R.id.spinner);
        edt_price = findViewById(R.id.edt_price);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        edt_drug_name.setText(drug.getDrugName());
//        edt_unit.setText(drug.unit+"");
        edt_amount.setText(drug.getAmount()+"");
        edt_price.setText(drug.getPrice()+"");
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        eventSpinner();
    }

    public void eventSpinner(){
        listUnit.add("Chai");
        listUnit.add("Viên");
        listUnit.add("Vỉ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1,listUnit);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drug.setUnit(listUnit.get(i));
                Log.d("AAA",drug.getUnit());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSave:
                if (drug.getDrugID() == null || drug.getDrugID().equals("")) {
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


                       drug.setDrugID(generateDrugStoreID());

                    drug.setDrugName(edt_drug_name.getText().toString().trim());
                    drug.setAmount(Integer.parseInt(edt_amount.getText().toString().trim()));
                    // todo: cái này là đơn vị tính

                    drug.setPrice(Integer.parseInt(edt_price.getText().toString().trim()));
                    if (DataManager.getInstance(context).insertDrug(drug)) {
                        callback.success();
                    } else {
                        callback.fail();
                        Toast.makeText(view.getContext(), "SAI CHO NAO V", Toast.LENGTH_SHORT).show();
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
                    drug.setDrugName(edt_drug_name.getText().toString().trim());
                    drug.setAmount(Integer.parseInt(edt_amount.getText().toString().trim()));
                    // todo: cái này là đơn vị tính

                    drug.setPrice(Integer.parseInt(edt_price.getText().toString().trim()));
                    if (DataManager.getInstance(context).updateDrug(drug)) {
                        callback.success();

                    }
                    dismiss();
                }
                break;
            case R.id.btnCancel:
                if (drug.getDrugID() != null && drug.getDrugID().equals("")) {
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
        return String.format("D%04d", n);
    }
}
