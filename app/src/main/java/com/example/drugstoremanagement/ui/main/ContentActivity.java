package com.example.drugstoremanagement.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
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
import org.jetbrains.annotations.NotNull;

public class ContentActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static CurrentFragment currentFragment = CurrentFragment.DRUG;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

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
        toolbar.setTitle(currentFragment == CurrentFragment.DRUG ? R.string.drug :
                currentFragment == CurrentFragment.DRUGSTORE ? R.string.drugstore :
                        currentFragment == CurrentFragment.BILL ? R.string.bill : R.string.statistic);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(v -> {
            openNavigationDrawer();
        });


        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(currentFragment == CurrentFragment.DRUG ? new DrugFragment() :
                currentFragment == CurrentFragment.DRUGSTORE ? new DrugStoreFragment() :
                        currentFragment == CurrentFragment.BILL ? new BillFragment() : new StatisticFragment());
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
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drug_page:
                if (currentFragment != CurrentFragment.DRUG) {
                    currentFragment = CurrentFragment.DRUG;
                    replaceFragment(new DrugFragment());
                    toolbar.setTitle(R.string.drug);
                }
                closeNavigationDrawer();
                return true;
            case R.id.drugstore_page:
                if (currentFragment != CurrentFragment.DRUGSTORE) {
                    currentFragment = CurrentFragment.DRUGSTORE;
                    replaceFragment(new DrugStoreFragment());
                    toolbar.setTitle(R.string.drugstore);
                }
                closeNavigationDrawer();
                return true;
            case R.id.bill_page:
                if (currentFragment != CurrentFragment.BILL) {
                    currentFragment = CurrentFragment.BILL;
                    replaceFragment(new BillFragment());
                    toolbar.setTitle(R.string.bill);
                }
                closeNavigationDrawer();
                return true;
            case R.id.statistic_page:
                if (currentFragment != CurrentFragment.STATISTIC) {
                    currentFragment = CurrentFragment.STATISTIC;
                    replaceFragment(new StatisticFragment());
                    toolbar.setTitle(R.string.statistic);
                }
                closeNavigationDrawer();
                return true;
            default:
                return false;
        }
    }

    public void openNavigationDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeNavigationDrawer() {
        if (drawerLayout!= null && drawerLayout.isOpen())
            drawerLayout.close();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}