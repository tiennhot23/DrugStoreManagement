package com.example.drugstoremanagement.ui.drug;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;

import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {

    private Context context;
    private RecyclerListener listener;
    private List<Drug> listDrug;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public DrugAdapter(Context context, RecyclerListener listener, List<Drug> listDrug) {
        this.context = context;
        this.listDrug = listDrug;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drug, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDrugName.setText(listDrug.get(position).getDrugName());
        holder.txtAmount.setText(listDrug.get(position).getAmount()+"");
        holder.txtUnit.setText(listDrug.get(position).getUnit());
        holder.txtPrice.setText(String.valueOf(listDrug.get(position).getPrice()));
        viewBinderHelper.bind(holder.swipeRevealLayout,listDrug.get(position).getDrugID());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.getInstance(context).delete(listDrug.get(holder.getAdapterPosition()));
                listDrug.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDrug.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnUpdate;
        TextView txtDrugName,txtAmount,txtUnit,txtPrice;
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            txtDrugName = itemView.findViewById(R.id.txtDrugName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtUnit = itemView.findViewById(R.id.txt_unit);

            txtPrice = itemView.findViewById(R.id.tvPrice);
            swipeRevealLayout = itemView.findViewById(R.id.SwipeLayout);
            linearLayout = itemView.findViewById(R.id.layout_delete);

            btnUpdate.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition());
            });

        }
    }

}
