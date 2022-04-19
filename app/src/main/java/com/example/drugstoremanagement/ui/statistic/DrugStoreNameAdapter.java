package com.example.drugstoremanagement.ui.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.db.model.DrugStore;

import java.util.List;

public class DrugStoreNameAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<DrugStore> drugStores;

    public DrugStoreNameAdapter(@NonNull Context context, int resource, int textViewResourceId, List<DrugStore> drugStores) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.drugStores = drugStores;
        this.resource = resource;
    }


    @Override
    public int getCount() {
        return drugStores.size();
    }

    @Override
    public String getItem(int i) {
        return drugStores.get(i).getDrugStoreName();
    }

    public String getItemID(int position) {
        return drugStores.get(position).getDrugStoreID();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = LayoutInflater.from(context).inflate(resource, viewGroup, false);
        TextView txtDrugStoreName = row.findViewById(R.id.txt_drugstore_name);
        txtDrugStoreName.setText(drugStores.get(i).getDrugStoreName());
        return row;
    }
}
