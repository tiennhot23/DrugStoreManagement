package com.example.drugstoremanagement.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.base.BaseActivity;
import com.example.drugstoremanagement.ui.bill.BillFragment;
import com.example.drugstoremanagement.ui.drug.DrugFragment;
import com.example.drugstoremanagement.ui.drugstore.DrugStoreFragment;
import com.example.drugstoremanagement.ui.statistic.StatisticFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;


import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static CurrentFragment currentFragment = CurrentFragment.DRUG;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    Fragment[] fragments = new Fragment[] {new DrugFragment(), new DrugStoreFragment(), new BillFragment(), new StatisticFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        setup();
    }

    @Override
    protected void setup() {
        bindView();
        currentFragment = (CurrentFragment) getIntent().getSerializableExtra("currentFragment");
        loadFragment();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(v -> {
            openNavigationDrawer();
        });

        navigationView.setNavigationItemSelectedListener(this);


    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout!= null && drawerLayout.isOpen())
            drawerLayout.close();
        else super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drug_page:
                if (currentFragment != CurrentFragment.DRUG) {
                    currentFragment = CurrentFragment.DRUG;
                }
                break;
            case R.id.drugstore_page:
                if (currentFragment != CurrentFragment.DRUGSTORE) {
                    currentFragment = CurrentFragment.DRUGSTORE;
                }
                break;
            case R.id.bill_page:
                if (currentFragment != CurrentFragment.BILL) {
                    currentFragment = CurrentFragment.BILL;
                }
                break;
            case R.id.statistic_page:
                if (currentFragment != CurrentFragment.STATISTIC) {
                    currentFragment = CurrentFragment.STATISTIC;
                }
                break;
            default: return false;
        }
        loadFragment();
        closeNavigationDrawer();
        return true;
    }

    public void openNavigationDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeNavigationDrawer() {
        if (drawerLayout!= null && drawerLayout.isOpen())
            drawerLayout.close();
    }

    private void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragments[currentFragment.position]);
        fragmentTransaction.commit();
        toolbar.setTitle(currentFragment.labelID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(ContentActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}