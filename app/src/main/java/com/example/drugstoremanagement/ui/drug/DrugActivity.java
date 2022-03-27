package com.example.drugstoremanagement.ui.drug;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.RecyclerListener;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.ui.base.BaseActivity;

import com.example.drugstoremanagement.ui.drugstore.DrugStoreAdapter;
import com.example.drugstoremanagement.ui.drugstore.DrugStoreDialog;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;


public class DrugActivity extends BaseActivity implements View.OnClickListener,DrugDialog.Callback, RecyclerListener {
    private EditText txtSearch;
    ImageView btn_search,btn_back, btn_add;
    FlowLayout historySearch;
    RecyclerView recyclerViewDrug;
    List<Drug> listDrug = new ArrayList<>();
    DrugDialog drugDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        setup();


    }

    public void setup(){
        setControl();
        listDrug = DataManager.getInstance(this).getDrug();
        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        recyclerViewDrug.setLayoutManager(new LinearLayoutManager(this));
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation_left_to_right);
        recyclerViewDrug.setLayoutAnimation(layoutAnimationController);
        recyclerViewDrug.setAdapter(new DrugAdapter(this, this, listDrug));




    }
    @SuppressLint("NotifyDataSetChanged")
    private void searchDrug(String query) {
        listDrug.clear();
        listDrug.addAll(DataManager.getInstance(this).findDrug(query));
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation_left_to_right);
        recyclerViewDrug.setLayoutAnimation(layoutAnimationController);
        if (recyclerViewDrug.getAdapter() != null) recyclerViewDrug.getAdapter().notifyDataSetChanged();
    }
//    public void setAnimation(int animation){
//
//    }

    public void setControl(){
        txtSearch = findViewById(R.id.edt_search);
        btn_search = findViewById(R.id.btn_search);
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        historySearch = findViewById(R.id.history_layout);
        recyclerViewDrug = findViewById(R.id.recycleViewDrug);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                addDrug();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                searchDrug(txtSearch.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void addDrug() {
        Drug drug = new Drug();
        listDrug.add(drug);
        drugDialog = new DrugDialog(this, this, drug);
        drugDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void success() {
        showMessage(R.string.save_success);
        if (recyclerViewDrug.getAdapter() != null) recyclerViewDrug.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void fail() {
        showMessage(R.string.save_fail);
        if (listDrug != null && listDrug.size() > 0) {
            listDrug.remove(listDrug.size() - 1);
        }
    }

    @Override
    public void onItemClick(int position) {
        drugDialog = new DrugDialog(this, this, listDrug.get(position));
        drugDialog.show();
    }
}