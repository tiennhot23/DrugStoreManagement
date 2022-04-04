package com.example.drugstoremanagement.ui.drugstore;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrug;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrugstore;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.nex3z.flowlayout.FlowLayout;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DrugStoreFragment extends BaseFragment implements View.OnClickListener, DrugStoreDialog.Callback, RecyclerListener {

    private EditText edtSearch;
    private RecyclerView recycler;
    private FlowLayout historySearchLayout;
    private ImageView btnAdd, btnSearch;
    private DrugStoreDialog dialogDrugStore;

    private List<DrugStore> drugStores;

    public DrugStoreFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugstore, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        setupView(view);
        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        drugStores = DataManager.getInstance(getContext()).getDrugStore();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_left_to_right);
        recycler.setLayoutAnimation(layoutAnimationController);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new DrugStoreAdapter(getContext(), this, drugStores));

        loadHistorySearch();

    }

    private void loadHistorySearch() {
        DataManager.getInstance(getContext()).getHistorySearchDrugstore().observe(this, historySearches -> {
            if(historySearches.size() > 10){
                DataManager.getInstance(getContext()).deleteHistorySearchDrugstore(historySearches.get(0));
            }
            historySearchLayout.removeAllViews();
            for(int i=0; i<historySearches.size(); i++) {
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10,10,10,10);
                textView.setId(i);
                textView.setLayoutParams(params);
                textView.setBackground(getResources().getDrawable(R.drawable.search_item));
                textView.getBackground().setAlpha(200);
                textView.setPadding(20,10,20,10);
                textView.setText(historySearches.get(i).getSearch());
                textView.setTextColor(Color.WHITE);
                historySearchLayout.addView(textView, 0);

                textView.setOnClickListener(v -> {
                    edtSearch.setText(textView.getText());
                    search(textView.getText().toString());
                });
            }
        });
    }

    private void setupView(View view) {
        edtSearch = view.findViewById(R.id.edt_search);
        recycler = view.findViewById(R.id.recycler);
        btnAdd = view.findViewById(R.id.btn_add);
        btnSearch = view.findViewById(R.id.btn_search);
        historySearchLayout = view.findViewById(R.id.history_layout);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void search(String query) {
        drugStores.clear();
        drugStores.addAll(DataManager.getInstance(getContext()).findDrugStore(query));
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_left_to_right);
        recycler.setLayoutAnimation(layoutAnimationController);
        if (recycler.getAdapter() != null) recycler.getAdapter().notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                addDrugStore();
                break;
            case R.id.btn_search:
                String title = edtSearch.getText().toString().trim();
                if(!title.equals(""))
                    DataManager.getInstance(getContext()).insertHistorySearchDrugstore(new HistorySearchDrugstore(title));
                search(edtSearch.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void addDrugStore() {
        DrugStore drugStore = new DrugStore();
        drugStores.add(drugStore);
        dialogDrugStore = new DrugStoreDialog(getContext(), this, drugStore);
        dialogDrugStore.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void success() {
        showPopupMessage(R.string.save_success, R.raw.success);
        if (recycler.getAdapter() != null) recycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void fail() {
        showPopupMessage(R.string.save_fail, R.raw.error);
        if (drugStores != null && drugStores.size() > 0) {
            drugStores.remove(drugStores.size() - 1);
        }
    }

    @Override
    public void onItemClick(int position) {
        dialogDrugStore = new DrugStoreDialog(getContext(), this, drugStores.get(position));
        dialogDrugStore.show();
    }
}
