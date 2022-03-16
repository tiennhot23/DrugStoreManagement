package com.example.drugstoremanagement.ui.drugstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.db.model.DrugStore;

import java.util.List;

public class DrugStoreAdapter extends RecyclerView.Adapter<DrugStoreAdapter.ViewHolder> {

    private Context context;
    private RecyclerListener listener;
    private List<DrugStore> drugStores;

    public DrugStoreAdapter(Context context, RecyclerListener listener, List<DrugStore> drugStores) {
        this.context = context;
        this.drugStores = drugStores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drugstore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDrugStoreName.setText(drugStores.get(position).drugStoreName);
        holder.txtAddress.setText(drugStores.get(position).address);
    }

    @Override
    public int getItemCount() {
        return drugStores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnUpdate;
        TextView txtAddress, txtDrugStoreName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnUpdate = itemView.findViewById(R.id.btn_update);
            txtAddress = itemView.findViewById(R.id.txt_address);
            txtDrugStoreName = itemView.findViewById(R.id.txt_drugstore_name);

            btnUpdate.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition());
            });

        }
    }

}
