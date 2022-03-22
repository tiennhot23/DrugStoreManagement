package com.example.drugstoremanagement.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.cardview.widget.CardView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.base.BaseActivity;
import com.example.drugstoremanagement.ui.drug.DrugActivity;
import com.example.drugstoremanagement.ui.drugstore.DrugStoreActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    CardView cardDrug, cardDrugStore, cardBill, cardStatistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

    }

    @Override
    protected void setup() {
        setupView();
        cardDrug.setOnClickListener(this);
        cardDrugStore.setOnClickListener(this);
        cardBill.setOnClickListener(this);
        cardStatistic.setOnClickListener(this);

        cardDrug.setVisibility(View.VISIBLE);
        cardDrug.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
        cardDrug.postDelayed(() -> {
            cardDrugStore.setVisibility(View.VISIBLE);
            cardDrugStore.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
            cardDrugStore.postDelayed(() -> {
                cardBill.setVisibility(View.VISIBLE);
                cardBill.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
                cardBill.postDelayed(() -> {
                    cardStatistic.setVisibility(View.VISIBLE);
                    cardStatistic.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
                },200);
            },200);
        },200);
    }

    private void setupView() {
        cardDrug = findViewById(R.id.card_drug);
        cardDrugStore = findViewById(R.id.card_drugstore);
        cardBill = findViewById(R.id.card_bill);
        cardStatistic = findViewById(R.id.card_statistic);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_drug:
                startActivity(new Intent(this, DrugActivity.class));
                break;
            case R.id.card_drugstore:
                startActivity(new Intent(this, DrugStoreActivity.class));
                break;
            case R.id.card_bill:
                break;
            case R.id.card_statistic:
                break;
            default:
                break;
        }
    }
}