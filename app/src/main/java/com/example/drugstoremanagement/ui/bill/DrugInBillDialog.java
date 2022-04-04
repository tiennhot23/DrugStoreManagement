package com.example.drugstoremanagement.ui.bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.viewmodel.DrugStoreViewModel;
import com.example.drugstoremanagement.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class DrugInBillDialog extends BaseDialog {

    private Context context;
    private List<Drug> drugs;
    private List<Drug> drugsSelected;
    private List<Drug> drugsSelectedTemp;
    private Callback callback;

    private ListView listItem;
    private Button btnSave, btnCancel;
    private DrugInBillAdapter adapter;

    interface Callback {
        void onButtonSaveClick();
    }

    public DrugInBillDialog(@NonNull Context context, List<Drug> drugs, List<Drug> drugsSelected, Callback callback) {
        super(context);
        this.context = context;
        this.drugs = drugs;
        this.drugsSelected = drugsSelected;
        this.callback = callback;

        drugsSelectedTemp = new ArrayList<>();
        int j = 0;
        for(int i=0; i<drugs.size(); i++) {
            drugsSelectedTemp.add(new Drug(drugs.get(i)));
            if (drugsSelected.size() > j && drugsSelected.get(j).getDrugID().equals(drugsSelectedTemp.get(i).getDrugID())) {
                drugsSelectedTemp.get(i).setAmount(drugsSelected.get(j).getAmount());
                j += 1;
            } else drugsSelectedTemp.get(i).setAmount(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        listItem = findViewById(R.id.list_item);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(v -> {
            drugsSelected.clear();
            for (int i=0; i<drugsSelectedTemp.size(); i++) {
                if (drugsSelectedTemp.get(i).getAmount() > 0) {
                    drugsSelected.add(drugsSelectedTemp.get(i));
                }
            }
            callback.onButtonSaveClick();
            dismiss();
        });
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        adapter = new DrugInBillAdapter(context, R.layout.item_drug_in_bill, drugs, drugsSelectedTemp);
        listItem.setAdapter(adapter);
    }

    @Override
    protected void setup() {
        setContentView(R.layout.list_drug_dialog);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.ZoomAnimation;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
