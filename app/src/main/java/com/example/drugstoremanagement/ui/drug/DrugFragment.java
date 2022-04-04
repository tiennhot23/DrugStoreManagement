package com.example.drugstoremanagement.ui.drug;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrug;
import com.example.drugstoremanagement.data.viewmodel.HistorySearchDrugViewModel;
import com.example.drugstoremanagement.data.viewmodel.HistorySearchDrugstoreViewModel;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.nex3z.flowlayout.FlowLayout;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends BaseFragment implements View.OnClickListener,DrugDialog.Callback, RecyclerListener {

    private EditText txtSearch;
    private ImageView btn_search, btn_add;
    private FlowLayout historySearchLayout;
    private RecyclerView recyclerViewDrug;
    private List<Drug> listDrug = new ArrayList<>();
    private DrugDialog drugDialog;

    private HistorySearchDrugViewModel historySearchDrugViewModel;

    public DrugFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        setControl(view);
        listDrug = DataManager.getInstance(getContext()).getDrug();
        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        recyclerViewDrug.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_left_to_right);
        recyclerViewDrug.setLayoutAnimation(layoutAnimationController);
        recyclerViewDrug.setAdapter(new DrugAdapter(getContext(), this, listDrug));

        loadHistorySearch();

    }

    private void loadHistorySearch() {
        historySearchDrugViewModel = ViewModelProviders.of(getBaseActivity(), new HistorySearchDrugViewModel.Factory(getContext())).get(HistorySearchDrugViewModel.class);
        historySearchDrugViewModel.getHistorySearchDrugs().observe(this, historySearches -> {
            if(historySearches.size() > 10){
                historySearchDrugViewModel.deleteHistorySearchDrug(historySearches.get(0));
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
                    txtSearch.setText(textView.getText());
                    searchDrug(textView.getText().toString());
                });
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchDrug(String query) {
        listDrug.clear();
        listDrug.addAll(DataManager.getInstance(getContext()).findDrug(query));
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_left_to_right);
        recyclerViewDrug.setLayoutAnimation(layoutAnimationController);
        if (recyclerViewDrug.getAdapter() != null) recyclerViewDrug.getAdapter().notifyDataSetChanged();
    }
//    public void setAnimation(int animation){
//
//    }

    public void setControl(View view){
        txtSearch = view.findViewById(R.id.edt_search);
        btn_search = view.findViewById(R.id.btn_search);
        btn_add = view.findViewById(R.id.btn_add);
        historySearchLayout = view.findViewById(R.id.history_layout);
        recyclerViewDrug = view.findViewById(R.id.recycleViewDrug);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                addDrug();
                break;
            case R.id.btn_search:
                String title = txtSearch.getText().toString().trim();
                if(!title.equals(""))
                    historySearchDrugViewModel.insertHistorySearchDrug(new HistorySearchDrug(title));
                searchDrug(txtSearch.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void addDrug() {
        Drug drug = new Drug();
        listDrug.add(drug);
        drugDialog = new DrugDialog(getContext(), this, drug);
        drugDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void success() {
        showPopupMessage(R.string.save_success, R.raw.success);
        if (recyclerViewDrug.getAdapter() != null) recyclerViewDrug.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void fail() {
        showPopupMessage(R.string.save_fail, R.raw.error);
        if (listDrug != null && listDrug.size() > 0) {
            listDrug.remove(listDrug.size() - 1);
        }
    }

    @Override
    public void onItemClick(int position) {
        drugDialog = new DrugDialog(getContext(), this, listDrug.get(position));
        drugDialog.show();
    }
}
