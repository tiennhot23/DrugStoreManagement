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

public class DetailBillAdapter extends ArrayAdapter<Drug> {

    private List<Drug> drugsSelected;
    private Context context;
    private int resource;

    public DetailBillAdapter(@NonNull Context context, int resource, List<Drug> drugsSelected) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
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
        TextView txtDrugName, txtAmount, txtPrice, txtUnit, txtTotal;

        txtDrugName = convertView.findViewById(R.id.txt_drug_name);
        txtAmount = convertView.findViewById(R.id.txt_amount);
        txtPrice = convertView.findViewById(R.id.txt_price);
        txtUnit = convertView.findViewById(R.id.txt_unit);
        txtTotal = convertView.findViewById(R.id.txt_total);

        txtDrugName.setText(drugsSelected.get(position).getDrugName());
        txtAmount.setText(String.valueOf(drugsSelected.get(position).getAmount()));
        txtPrice.setText(String.valueOf(drugsSelected.get(position).getPrice()));
        txtUnit.setText(drugsSelected.get(position).getUnit());
        txtTotal.setText(String.valueOf(drugsSelected.get(position).getAmount() * drugsSelected.get(position).getPrice()));
        return convertView;
    }
}
