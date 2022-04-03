package com.example.drugstoremanagement.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    CardView cardDrug, cardDrugStore, cardBill, cardStatistic;
    RelativeLayout search;

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
        search.setOnClickListener(this);

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
        search = findViewById(R.id.search);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.card_drug:
            case R.id.search:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("currentFragment", CurrentFragment.DRUG);
                startActivity(intent);
                break;
            case R.id.card_drugstore:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("currentFragment", CurrentFragment.DRUGSTORE);
                startActivity(intent);
                break;
            case R.id.card_bill:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("currentFragment", CurrentFragment.BILL);
                startActivity(intent);
                break;
            case R.id.card_statistic:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("currentFragment", CurrentFragment.STATISTIC);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}