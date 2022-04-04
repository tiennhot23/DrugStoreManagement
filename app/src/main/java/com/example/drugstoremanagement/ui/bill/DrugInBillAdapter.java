package com.example.drugstoremanagement.ui.bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.db.model.Drug;

import java.util.List;

public class DrugInBillAdapter extends ArrayAdapter<Drug> {

    private List<Drug> drugsSelected;
    private List<Drug> drugs;
    private Context context;
    private int resource;

    public DrugInBillAdapter(@NonNull Context context, int resource, List<Drug> drugs, List<Drug> drugsSelected) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.drugs = drugs;
        this.drugsSelected = drugsSelected;
    }

    @Override
    public int getCount() {
        return drugsSelected.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txtDrugName, txtAmount, txtCurrentAmount;
        ImageButton btnMinus, btnPlus;

        txtDrugName = convertView.findViewById(R.id.txt_drug_name);
        txtAmount = convertView.findViewById(R.id.txt_amount);
        txtCurrentAmount = convertView.findViewById(R.id.txt_current_amount);
        btnMinus = convertView.findViewById(R.id.btn_minus);
        btnPlus = convertView.findViewById(R.id.btn_plus);

        txtDrugName.setText(drugs.get(position).getDrugName());
        txtAmount.setText(String.valueOf(drugs.get(position).getAmount()));
        txtCurrentAmount.setText(String.valueOf(drugsSelected.get(position).getAmount()));

        btnMinus.setOnClickListener(v -> {
            drugsSelected.get(position).setAmount(Math.max(0, drugsSelected.get(position).getAmount() - 1));
            txtCurrentAmount.setText(String.valueOf(drugsSelected.get(position).getAmount()));
        });

        btnPlus.setOnClickListener(v -> {
            drugsSelected.get(position).setAmount(Math.min(drugs.get(position).getAmount(), drugsSelected.get(position).getAmount() + 1));
            txtCurrentAmount.setText(String.valueOf(drugsSelected.get(position).getAmount()));
        });
        return convertView;
    }
}
